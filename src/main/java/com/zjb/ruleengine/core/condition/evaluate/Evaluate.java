
package com.zjb.ruleengine.core.condition.evaluate;

import com.zjb.ruleengine.core.enums.Symbol;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈执行接口〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/7/1
 * @since 1.0.0
 */
public interface Evaluate extends Serializable {

    boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType);
}