package com.zjb.ruleengine.core.rule;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.value.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:40
 */
public class Rule extends AbstractRule implements Serializable {

    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = 6727628276386208126L;

    private AbstractCondition condition;

    public Rule(String id, AbstractCondition condition, Value action) {
        super(id,condition,action);
    }

    @Override
    public Boolean executeCondition(Context context) {
        return condition.evaluate(context);
    }

    @Override
    public void build() {
        this.condition = super.getCondition().build();
    }
}
