package com.zjb.ruleengine.core.rule;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.decistion.DecisionCondition;
import com.zjb.ruleengine.core.decistion.TreeNodeCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:41
 */
public class DecisionRuleSet extends RuleSet {
    private static final Logger log = LogManager.getLogger();

    private static final long serialVersionUID = 545078698865785974L;

    public static final DecisionCondition ANY_MATCH = new DecisionCondition("root", new Constant(DataTypeEnum.BOOLEAN, true),
            Symbol.boolean_eq, new Constant(DataTypeEnum.BOOLEAN, true));

    @JSONField(serialize = false)
    private TreeNodeCondition rootCondition;



    public DecisionRuleSet(String id,List<? extends AbstractRule> rules) {
        super(id,rules);
    }

    @Override
    public Object execute(Context context) {
        return super.execute(context);
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
        List<AbstractRule> rules = this.getRules();
        for (AbstractRule rule : rules) {
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


}
