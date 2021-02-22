package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum MatchEnum implements Serializable {
    ALL_MATCH(1), ONE_MATCH(2);
    /**
     * @author 赵静波
     * @date 2020-08-25 09:54:30
     */
    private Integer status;
    private static Map<Integer, MatchEnum> map = new HashMap<>();
    private static Map<String, MatchEnum> nameMap = new HashMap<>();

    static {
        MatchEnum[] enumConstants = MatchEnum.class.getEnumConstants();
        for (MatchEnum enumConstant : enumConstants) {
            map.put(enumConstant.status, enumConstant);
            nameMap.put(enumConstant.name(), enumConstant);
        }
    }

    MatchEnum(Integer status) {
        this.status = status;
    }

    public static MatchEnum getStatus(Integer status) {
        return map.get(status);
    }

    public static MatchEnum getByName(String name) {
        return nameMap.get(name);
    }
}
