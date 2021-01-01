package com.zjb.ruleengine.core.decistion;

import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Value;

/**
 * @author 赵静波
 * @date 2020-08-25 15:51:02
 */
public class DecisionCondition extends DefaultCondition {

    public DecisionCondition(String id, Value leftValue, Symbol symbol, Value rightValue) {
        super(id, leftValue, symbol, rightValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DecisionCondition)) {
            return false;
        }
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
