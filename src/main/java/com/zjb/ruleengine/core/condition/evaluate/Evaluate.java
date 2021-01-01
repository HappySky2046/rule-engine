
package com.zjb.ruleengine.core.condition.evaluate;

import com.zjb.ruleengine.core.enums.Symbol;

import java.io.Serializable;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:00
 */
public interface Evaluate extends Serializable {

    boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType);
}