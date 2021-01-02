package com.zjb.ruleengine.core;

import com.zjb.ruleengine.core.rule.AbstractRule;

import java.io.Serializable;

/**
 * @author 赵静波
 * @date 2020-09-30 16:22:06
 */
public interface RuleEngine extends Serializable {
    /**
     * @return void
     * @Author
     * @Description 对指定上下文运行指定规则编码的规则
     * @Param context - 已装载的上下文
     * @Param ruleSetCode - 需要执行的规则编码
     **/
    Object execute(String ruleSetCode,Context context);

    /**
     * @Author zjb
     * @Description 加入一个规则
     * @Date 15:52 2019-06-18
     * @Param [ruleSet]
     * @return void
     **/
    void addRule(AbstractRule rule);

    /**
     * 根据ruleSetCode 删除 RuleSet
     * @param ruleId
     * @return
     */
    void removeRule(String ruleId);


}
