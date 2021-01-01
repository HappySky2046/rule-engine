
package com.zjb.ruleengine.core.condition.evaluate;

import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author 赵静波
 * @date 2020-08-24 18:41:23
 */
public class StringEvaluateStrategy implements Evaluate {

    private static final Logger log = LogManager.getLogger();

    private static Evaluate strategy = new StringEvaluateStrategy();

    public static Evaluate getInstance() {
        return strategy;
    }

    @Override
    public boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType) {
        if (leftValue == null || rightValue == null) {
            return false;
        }
        if (!(leftValue instanceof String)) {
            throw new RuleEngineException("左值必须是String");
        }
        if (operatorType == Symbol.str_in) {
            return BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        } else if (operatorType == Symbol.str_not_in) {
            return !BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        }


        if (!(rightValue instanceof String)) {
            throw new RuleEngineException("右值必须是String");
        }
        String rightStr = String.valueOf(rightValue);
        String leftStr = String.valueOf(leftValue);
        switch (operatorType) {
            case str_eq:
                return (leftValue.equals(rightValue));
            case str_ne:
                return !(leftValue.equals(rightValue));
            case str_start_with:
                return leftStr.startsWith(rightStr);
            case str_end_with:
                return leftStr.endsWith(rightStr);
            case str_not_start_with:
                return !leftStr.startsWith(rightStr);
            case str_not_end_with:
                return !leftStr.endsWith(rightStr);
            case str_index_of:
                return rightStr.indexOf(leftStr) >= 0;
            case str_not_index_of:
                return rightStr.indexOf(leftStr) < 0;
            default:
                log.warn("操作符" + operatorType + "不匹配");
                throw new RuleEngineException("操作符 " + operatorType + " 不匹配");
        }
    }
}