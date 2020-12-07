
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
 * 〈一句话功能简述〉<br>
 * 〈String 处理方案〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/7/1
 * @since 1.0.0
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
        if (!(leftValue instanceof Number)) {
            throw new RuleEngineException("左值必须是number");
        }
        if (operatorType == Symbol.in) {
            return BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        } else if (operatorType == Symbol.not_in) {
            return !BooleanEvaluateStrategy.getInstance().in(rightValue, leftValue);
        }
        if (!(rightValue instanceof Number)) {
            throw new RuleEngineException("右值必须是number");
        }
        if (leftValue == null || rightValue == null) {
            return false;
        }
        String leftTempString = leftValue + "";
        String rightTempString = rightValue + "";
        if (leftTempString.equals(BLANK_STRING) || rightTempString.equals(BLANK_STRING) ||
                StringUtils.isBlank(leftTempString) || StringUtils.isBlank(rightTempString)) {
            return false;
        }
        Number leftNumber = NumberUtils.createNumber(leftTempString);
        Number rightNumber = NumberUtils.createNumber(rightTempString);
        if (!(leftNumber instanceof Comparable) || !(rightNumber instanceof Comparable)) {
            throw new IllegalArgumentException("数据类型异常");
        }
        Comparable left;
        Comparable right;
        if (leftTempString.contains(StrUtil.DOT) || rightTempString.contains(StrUtil.DOT)) {
            left = new BigDecimal(leftTempString + "");
            right = new BigDecimal(rightTempString + "");
        } else {
            left = new BigInteger(leftTempString + "");
            right = new BigInteger(rightTempString + "");
        }
        int compare = left.compareTo(right);
        switch (operatorType) {
            case eq:
                return compare == 0;
            case gt:
                return compare > 0;
            case lt:
                return compare < 0;
            case ge:
                return compare >= 0;
            case le:
                return compare <= 0;
            case ne:
                return compare != 0;
            default:
                log.warn("操作符" + operatorType + "不匹配");
                throw new RuleEngineException("操作符 " + operatorType + " 不匹配");
        }
    }


}