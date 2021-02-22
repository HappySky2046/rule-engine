package com.zjb.ruleengine.core.rule;

import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:40
 */
public class Rule extends AbstractRule implements Serializable {

    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = 6727628276386208126L;

    private AbstractCondition condition;


    /**
     * 动作，如果condition==true,则执行actionValue并返回
     */
    private Value action;

    public Rule(String id, AbstractCondition condition, Value action) {
        super(id);
        this.condition=condition;
        this.action = action;
    }

    @Override
    public Collection<Element> collectParameter() {
        final HashSet<Element> parameterNames = Sets.newHashSet();
        parameterNames.addAll(condition.collectParameter());
        parameterNames.addAll(action.collectParameter());
        return Collections.unmodifiableCollection(parameterNames);
    }

    @Override
    protected Object doExecute(Context context) {
        if (condition.evaluate(context)) {
            return action.getValue(context);
        }
        return RuleResultEnum.NULL;
    }

    @Override
    public int getWeight() {
        return condition.getWeight() + action.getWeight();
    }

    @Override
    public void build() {
        this.condition = this.condition.build();
    }

    public AbstractCondition getCondition() {
        return condition;
    }

    public Value getAction() {
        //todo 考虑不可变
        return action;
    }
}
