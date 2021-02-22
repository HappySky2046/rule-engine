package com.zjb.ruleengine.core;

/**
 * @author 赵静波
 * @date 2020-08-28 16:55:37
 */
public interface Weight {

    int HIGH = 0;
    int LOW = 2;
    int MID = 1;

    /**
     * 获取权重
     *
     * @return
     */
    int getWeight();
}
