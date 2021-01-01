
package com.zjb.ruleengine.core.condition.evaluate;

import cn.hutool.core.text.StrFormatter;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-08-24 18:40:22
 */
public class BooleanEvaluateStrategy implements Evaluate {
    private static final Logger log = LogManager.getLogger();

    private static BooleanEvaluateStrategy strategy = new BooleanEvaluateStrategy();
    public static String TRUE_STRING = "true";
    public static String FALSE_STRING = "false";

    public static BooleanEvaluateStrategy getInstance() {
        return strategy;
    }

    @Override
    public boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType) {
        if (leftValue == null || rightValue == null) {
            return false;
        }
        if (!(leftValue instanceof Boolean)) {
            throw new RuleEngineException(StrFormatter.format("左值:{} 不是boolean类型", leftValue));
        }

        Boolean leftBool = (Boolean) leftValue;
        switch (operatorType) {
            case boolean_in:
                return in(rightValue, leftBool);
            case boolean_not_in:
                return !in(rightValue, leftBool);
            case boolean_eq:
                if (!(rightValue instanceof Boolean)) {
                    throw new RuleEngineException(StrFormatter.format("右值:{} 不是boolean类型", rightValue));
                }
                Boolean rightBool = (Boolean) rightValue;
                return leftBool.compareTo(rightBool) == 0;
            case boolean_ne:
                if (!(rightValue instanceof Boolean)) {
                    throw new RuleEngineException(StrFormatter.format("右值:{} 不是boolean类型", rightValue));
                }
                rightBool = (Boolean) rightValue;
                return leftBool.compareTo(rightBool) != 0;
            default:
                log.warn("操作符" + operatorType + "不匹配");
                throw new RuleEngineException("操作符 " + operatorType + " 不匹配");
        }
    }

    public boolean in(Object rightValue, Object leftValue) {
        if (!(rightValue instanceof Collection)) {
            throw new RuleEngineException(StrFormatter.format("右值:{} 不是集合类型", rightValue));
        }
        Collection right = (Collection) rightValue;
        for (Object o : right) {
            if (Objects.equals(o + "", leftValue + "")) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(strategy.evaluate(true, Arrays.asList(true, false), Symbol.boolean_in));
        System.out.println(strategy.evaluate(false, Arrays.asList(false), Symbol.boolean_not_in));

    }
}