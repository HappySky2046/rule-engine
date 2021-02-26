
package com.zjb.ruleengine.core.condition.evaluate;

import cn.hutool.core.util.StrUtil;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:41
 */
public class NumberEvaluateStrategy implements Evaluate {
    private static final Logger log = LogManager.getLogger();

    private static NumberEvaluateStrategy strategy = new NumberEvaluateStrategy();

    public static NumberEvaluateStrategy getInstance() {
        return strategy;
    }

    private static final String BLANK_STRING = "null";

    @Override
    @SuppressWarnings("unchecked")
    public boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType) {

        if (leftValue == null || rightValue == null) {
            return false;
        }
        if (!(leftValue instanceof Number)) {
            throw new RuleEngineException("左值必须是number");
        }
        if (operatorType == Symbol.number_in) {
            return BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        } else if (operatorType == Symbol.not_in) {
            return !BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        }
        if (!(rightValue instanceof Number)) {
            throw new RuleEngineException("右值必须是number");
        }


        BigDecimal left = new BigDecimal(String.valueOf(leftValue));
        BigDecimal right = new BigDecimal(String.valueOf(rightValue));
        int compare = left.compareTo(right);
        switch (operatorType) {
            case number_eq:
                return compare == 0;
            case number_gt:
                return compare > 0;
            case number_lt:
                return compare < 0;
            case number_ge:
                return compare >= 0;
            case number_le:
                return compare <= 0;
            case number_ne:
                return compare != 0;
            default:
                log.warn("操作符" + operatorType + "不匹配");
                throw new RuleEngineException("操作符 " + operatorType + " 不匹配");
        }
    }


}