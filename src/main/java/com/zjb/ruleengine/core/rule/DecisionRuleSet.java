package com.zjb.ruleengine.core.rule;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.decistion.TreeNodeCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:41
 */
public class DecisionRuleSet extends AbstractRule {
    private static final Logger log = LogManager.getLogger();

    private static final long serialVersionUID = 545078698865785974L;

    public static final DefaultCondition ANY_MATCH = new DefaultCondition("root", new Constant(DataTypeEnum.BOOLEAN, true),
            Symbol.boolean_eq, new Constant(DataTypeEnum.BOOLEAN, true));

    @JSONField(serialize = false)
    private TreeNodeCondition rootCondition;

    private List<Rule> rules;

    private RuleSetExecutePolicyEnum policy = RuleSetExecutePolicyEnum.ONE;


    public DecisionRuleSet(String id, List<Rule> rules) {
        super(id);
        this.rules = rules;
    }

    @Override
    public Object doExecute(Context context) {
        final LinkedHashMap<TreeNodeCondition, Object> nodeConditions = rootCondition.getChildrenNodeConditions();



        return super.execute(context);
    }


    @Override
    public int getWeight() {
        return rules.stream().mapToInt(AbstractRule::getWeight).sum();
    }

    @Override
    public Collection<Element> collectParameter() {
        final Set<Element> collect = rules.stream().flatMap(rule -> rule.collectParameter().stream()).collect(Collectors.toSet());
        return Collections.unmodifiableSet(collect);
    }
    /**
     * 构建 决策表执行树
     */
    @Override
    public void build() {

        //初始化决策森林,
        this.rootCondition = new TreeNodeCondition();
        rootCondition.setNodeCondition(ANY_MATCH);

        //一条rule转换成一个decisionRule
        List<Rule> rules = this.rules;
        for (Rule rule : rules) {
            TreeNodeCondition childCondition = rootCondition;
            final AbstractCondition condition = rule.getCondition();
            if (condition instanceof ConditionGroup) {
                final ConditionGroup conditionGroup = (ConditionGroup) condition;
                final List<AbstractCondition> conditions = conditionGroup.getConditions();
                if (CollUtil.isNotEmpty(conditions)) {
                    for (AbstractCondition abstractCondition : conditions) {
                        final TreeNodeCondition treeNodeCondition = new TreeNodeCondition();
                        treeNodeCondition.setNodeCondition(abstractCondition);
                        if (childCondition.containChildNodeCondition(treeNodeCondition)) {
                            childCondition = childCondition.getChildNodeCondition(treeNodeCondition);
                        } else {
                            childCondition.addChildNodeCondition(treeNodeCondition);
                            childCondition = treeNodeCondition;
                        }

                    }
                }
            } else {
                final TreeNodeCondition treeNodeCondition = new TreeNodeCondition();
                treeNodeCondition.setNodeCondition(condition);
                childCondition.addChildNodeCondition(treeNodeCondition);
                childCondition = treeNodeCondition;
            }
            childCondition.setLeaf(true);
            childCondition.addResult(rule.getAction());
        }
    }

    public RuleSetExecutePolicyEnum getPolicy() {
        return policy;
    }

    public void setPolicy(RuleSetExecutePolicyEnum policy) {
        this.policy = policy;
    }
}
