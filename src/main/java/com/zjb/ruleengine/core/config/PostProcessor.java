package com.zjb.ruleengine.core.config;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.rule.AbstractRule;

/**
 * @author 赵静波
 * @date 2020-08-25 14:31:19
 */
public interface PostProcessor {
    /**
     * 在Action执行之前
     *
     * @param context 运行环境
     * @return
     */
    void postProcessorBeforeActionExecute(AbstractRule abstractRule, Context context);

    /**
     * 在Action执行之后，返回之前
     *
     * @param actionValue action的值
     * @return action的值，可以对Action的值进行修改
     */
    Object afterProcessorBeforeActionExecute(AbstractRule abstractRule, Context context, Object actionValue);
}
