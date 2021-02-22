package com.zjb.ruleengine.core.config;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.rule.AbstractRule;

/**
 * 规则执行前置处理器
 */
public interface PreProcessor {
    /**
     * 在规则执行之前
     *
     * @param context 运行环境
     * @return
     */
    void preProcessor(AbstractRule abstractRule, Context context);
}
