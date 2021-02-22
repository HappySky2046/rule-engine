
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
public enum StatusEnum implements Serializable {
    enable(1), disable(0);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    private static Map<Integer, StatusEnum> map = new HashMap<>();
    private static Map<String, StatusEnum> nameMap = new HashMap<>();

    static {
        StatusEnum[] enumConstants = StatusEnum.class.getEnumConstants();
        for (StatusEnum enumConstant : enumConstants) {
            map.put(enumConstant.status, enumConstant);
            nameMap.put(enumConstant.name(), enumConstant);
        }
    }

    StatusEnum(Integer status) {
        this.status = status;
    }

    public static StatusEnum getStatus(Integer status) {
        return map.get(status);
    }

    public static StatusEnum getByName(String name) {
        return nameMap.get(name);
    }

}