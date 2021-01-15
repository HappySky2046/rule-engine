package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum LoopStrategyEnum implements Serializable {
    HighestPriorityReturnAll(0), HighestPriorityReturnSignle(1), AllPriorityReturnAll(2), AllPriorityReturnSingle(3);
    /**
     * @author 赵静波
     * @date 2020-08-25 09:54:30
     */
    private Integer status;
    private static Map<Integer, LoopStrategyEnum> map = new HashMap<>();
    private static Map<String, LoopStrategyEnum> nameMap = new HashMap<>();

    static {
        LoopStrategyEnum[] enumConstants = LoopStrategyEnum.class.getEnumConstants();
        for (LoopStrategyEnum enumConstant : enumConstants) {
            map.put(enumConstant.status, enumConstant);
            nameMap.put(enumConstant.name(), enumConstant);
        }
    }

    LoopStrategyEnum(Integer status) {
        this.status = status;
    }

    public static LoopStrategyEnum getStatus(Integer status) {
        return map.get(status);
    }

    public static LoopStrategyEnum getByName(String status) {
        return nameMap.get(status);
    }

    public Integer getStatus() {
        return status;
    }


}