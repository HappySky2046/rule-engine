package com.zjb.ruleengine.core.rule;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.auth.IAuthorization;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.decistiontree.*;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class DecisionRuleSet extends RuleSet implements Serializable {

    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = 545078698865785974L;

    public static final DecisionCondition ANY_MATCH = new DecisionCondition("default", new Constant(DataTypeEnum.BOOLEAN, true),
            Symbol.boolean_eq, new Constant(DataTypeEnum.BOOLEAN, true));

    @JSONField(serialize = false)
    private DecisionForest decisionForest;

    private Map<String, Collection<String>> processRepetition;

    public DecisionRuleSet(String id, AbstractCondition condition, Value action, List<AbstractRule> rules, List<IAuthorization> authorizations, RuleSetExecutePolicyEnum policy, DecisionForest decisionForest, Map<String, Collection<String>> processRepetition) {
        super(id, condition, action, rules, authorizations, policy);
        this.decisionForest = decisionForest;
        this.processRepetition = processRepetition;
    }

    @Override
    public Boolean executeCondition(Context context) {
        return null;
    }

    @Override
    public String toJsonString() {
        /**
         *  没有必要格式化decisionForest，会通过{@link DecisionRuleSet.buildTree}构建
         */
        this.decisionForest = null;
        return super.toJsonString();
    }

    @Override
    public void parse(String json, RuleSetParser parser) {
        //RuleSetParser parser = DecisionRuleSetParser.getInstance();
        super.parse(json, parser);
    }

    @Override
    public Object execute(Context context) {
        return super.execute(context);
    }

    private static List executeOnce(Context context, DecisionForest decisionForest) {
        List<DecisionEngineTreeNode> decisionEngineTreeNodes = decisionForest.getDecisionEngineTreeNodes();
        List<Object> result = new ArrayList<>();
        for (DecisionEngineTreeNode decisionEngineTreeNode : decisionEngineTreeNodes) {
            deepFind(decisionEngineTreeNode, context, 0, result);
            // 当策略是遍历最高优先级时，搜到结果直接返回
            if (CollUtil.isNotEmpty(result)) {
                break;
            }
        }
        return result;
    }


    public static void deepFind(DecisionEngineTreeNode root, Context context, int curIndex, List<Object> results) {
        //如果是叶子节点，将结果加到结果集中
        if (root.isLeaf()) {
            for (Value value : root.getResults()) {
                Object result = value.getValue(context);
                if (result instanceof Collection) {
                    results.addAll((Collection) result);
                } else {
                    results.add(value.getValue(context));
                }
            }
            return;
        }
        for (DecisionEngineTreeNode decisionEngineTreeNode : root.getDecisionEngineTreeNodeList()) {
            if (decisionEngineTreeNode.getDecisionCondition().evaluate(context)) {
                deepFind(decisionEngineTreeNode, context, curIndex + 1, results);
            }
        }
    }


    @Override
    public void build() {

        //初始化决策森林,
        this.decisionForest = new DecisionForest();
        //一条rule转换成一个decisionRule
        List<AbstractRule> rules = this.getRules();
        List<DecisionRow> decisionRows = new ArrayList<>();
        for (AbstractRule rule : rules) {
            DecisionRow decisionRow = new DecisionRow();
            final AbstractCondition srcCondition = rule.getCondition();
            decisionRow.setResult(rule.getAction());

            //决策条件
            //List<ConditionGroup> conditionGroups = rule.getAction().getConditionGroups();
            //todo
            List<ConditionGroup> conditionGroups =null;
            List<AbstractCondition> conditions = conditionGroups.get(0).getConditions();

            List<DecisionColInfo> decisionColInfos = new ArrayList<>();
            for (AbstractCondition condition : conditions) {
                DefaultCondition normalCondition = (DefaultCondition) condition;
                DecisionColInfo decisionColInfo = new DecisionColInfo();
                //生成条件
                DecisionCondition decisionCondition = new DecisionCondition("", normalCondition.getLeftValue(), normalCondition.getSymbol(), (Constant) normalCondition.getRightValue());
                decisionCondition.setLeftValue(normalCondition.getLeftValue());
                decisionCondition.setSymbol(normalCondition.getSymbol());
                decisionCondition.setRightValue(normalCondition.getRightValue());
                decisionColInfo.setDecisionCondition(decisionCondition);
                decisionColInfos.add(decisionColInfo);
            }
            decisionRow.setDecisionColInfos(decisionColInfos);
            decisionRows.add(decisionRow);
        }
        //DecisionRows转换成rule
        //将决策行按优先级排序
        Map<Integer, List<DecisionRow>> decisionMap = decisionRows.stream().collect(
                Collectors.groupingBy(
                        DecisionRow::getPriority
                )
        );

        Map<Integer, DecisionEngineTreeNode> decisionEngineTreeNodeMap = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<Integer, List<DecisionRow>> entry : decisionMap.entrySet()) {
            DecisionEngineTreeNode root = new DecisionEngineTreeNode();
            List<DecisionRow> decisionRowByPriority = entry.getValue();
            for (DecisionRow decisionRow : decisionRowByPriority) {
                //递归地向下进行树的创建
                List<DecisionCondition> decisionEngineConditions = decisionRow.getDecisionColInfos().stream()
                        .map(DecisionColInfo::getDecisionCondition).collect(Collectors.toList());
                deepCreate(root, decisionEngineConditions, 0, root, decisionRow.getResult());
            }

            decisionEngineTreeNodeMap.put(entry.getKey(), root);
        }
        List<Integer> priorityKey = decisionMap.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List<DecisionEngineTreeNode> decisionEngineTreeNodes = priorityKey.stream().map(e -> {
            DecisionEngineTreeNode decisionEngineTreeNode = decisionEngineTreeNodeMap.get(e);
            return decisionEngineTreeNode;
        }).collect(Collectors.toList());

        this.decisionForest.setDecisionEngineTreeNodes(decisionEngineTreeNodes);
    }

    /**
     * @param curNode                  递归的上级结点
     * @param decisionEngineConditions 所有coditions
     * @param curIndex                 condition指针
     * @param root                     预留字段
     * @param result                   rule.result
     */
    private void deepCreate(DecisionEngineTreeNode curNode, List<DecisionCondition> decisionEngineConditions, int curIndex, DecisionEngineTreeNode root, Value result) {
        if (curIndex == decisionEngineConditions.size()) {
            // 已经没有条件了，当前节点是叶子节点
            curNode.setLeaf(true);
            curNode.getResults().add(result);
            return;
        }
        //获取当前的决策条件
        DecisionCondition curDecisionCondition = decisionEngineConditions.get(curIndex);
        if (curDecisionCondition == null) {
            /*条件为空, 表明当前列无论是什么值, 均能够匹配*/
            curDecisionCondition = ANY_MATCH;
        }
        List<DecisionEngineTreeNode> decisionEngineTreeNodeList = curNode.getDecisionEngineTreeNodeList();
        DecisionEngineTreeNode decisionEngineTreeNode = null;
        //如果不存在决策树子树节点，新建决策树节点
        for (DecisionEngineTreeNode tempDecisionEngineTreeNode : decisionEngineTreeNodeList) {
            if (tempDecisionEngineTreeNode.getDecisionCondition().equals(curDecisionCondition)) {
                decisionEngineTreeNode = tempDecisionEngineTreeNode;
                break;
            }
        }
        //节点为空，则新建，递归的进行创建节点
        if (decisionEngineTreeNode == null) {
            decisionEngineTreeNode = new DecisionEngineTreeNode();
            decisionEngineTreeNode.setDecisionCondition(curDecisionCondition);
            decisionEngineTreeNodeList.add(decisionEngineTreeNode);
        }
        deepCreate(decisionEngineTreeNode, decisionEngineConditions, curIndex + 1, root, result);
    }

    public DecisionForest getDecisionForest() {
        return decisionForest;
    }

    public void setDecisionForest(DecisionForest decisionForest) {
        this.decisionForest = decisionForest;
    }

    public Map<String, Collection<String>> getProcessRepetition() {
        return processRepetition;
    }

    public void setProcessRepetition(Map<String, Collection<String>> processRepetition) {
        this.processRepetition = processRepetition;
    }
}
