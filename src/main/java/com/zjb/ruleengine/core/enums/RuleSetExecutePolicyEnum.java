
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */
public enum RuleSetExecutePolicyEnum implements Serializable {
    /**
     * 规则按顺序触发成功一次即返回规则执行结果
     */
    ONE,
    /**
     * 规则随机触发成功一次即返回规则执行结果
     */
    ANY,
    /**
     * 获取所有 触发成功的规则结果
     */
    ALL;
    private static Map<String, RuleSetExecutePolicyEnum> map = new HashMap<>();

    static {
        RuleSetExecutePolicyEnum[] constants = RuleSetExecutePolicyEnum.class.getEnumConstants();
        for (RuleSetExecutePolicyEnum constant : constants) {
            map.put(constant.name(), constant);
        }
    }

    public static RuleSetExecutePolicyEnum getByName(String name) {

        return map.get(name);
    }


}