package com.zjb.ruleengine.core.value;

import com.zjb.ruleengine.core.function.GetPropertyFunction;

/**
 * 自动执行
 * 触发条件：
 * 1：{@link com.zjb.ruleengine.core.function.Function}的子类，
 * 2：参数对象有{@link Value}类型的成员变量
 * 3：该成员变量的类型需要是{@link Object}
 * 则在该函数执行的时候，获取的该成员变量的值 是 {@link Value#getValue(com.zjb.ruleengine.core.Context)}
 * <p>
 * 例如：
 * public class SubValue implements AutoExecute{
 * private Object fieldValue;
 * }
 * SubValue subValue=new SubValue();
 * Value value=new Variable();
 * subValue.setFieldValue(value)
 * <p>
 * 则  subValue.getFieldValue()==value.getValue() //获取的值是value.getValue()
 *
 *
 * <p>
 * 则
 *
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2021-01-10
 * @see {@link GetPropertyFunction.GetPropertyFunctionParameter}
 */
public interface AutoExecute {
}
