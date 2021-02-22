package com.zjb.ruleengine.core.utils;

import java.io.Serializable;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}