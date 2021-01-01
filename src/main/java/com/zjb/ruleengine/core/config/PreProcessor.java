package com.zjb.ruleengine.core.config;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.rule.AbstractRule;

/**
 * @author 赵静波
 * @date 2020-08-25 14:32:39
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
