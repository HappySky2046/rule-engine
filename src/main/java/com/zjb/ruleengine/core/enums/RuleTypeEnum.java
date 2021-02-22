
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈状态枚举类〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/7/18
 * @since 1.0.0
 */
public enum RuleTypeEnum implements Serializable {
    /**
     * POLICY_RULE:特殊策略规则
     * PRE_CONDITION_PRE:前置条件使用
     */
    SPECIAL(1), NORMAL(0), PRE_CONDITION_PRE(3);

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    private static Map<Integer, RuleTypeEnum> map = new HashMap<>();
    static {
        RuleTypeEnum[] enumConstants = RuleTypeEnum.class.getEnumConstants();
        for (RuleTypeEnum enumConstant : enumConstants) {
            map.put(enumConstant.status, enumConstant);
        }
    }
    RuleTypeEnum(Integer status) {
        this.status = status;
    }

    public static RuleTypeEnum getStatus(Integer status) {
        return map.get(status);
    }

}