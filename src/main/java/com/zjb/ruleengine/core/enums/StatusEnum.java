
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-08-24 18:42:50
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