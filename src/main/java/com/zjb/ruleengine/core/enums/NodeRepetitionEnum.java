package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum NodeRepetitionEnum implements Serializable {

    NONE(0), FRONT(1), BEHIND(2);

    private Integer status;
    private static Map<Integer, NodeRepetitionEnum> nodeRepetitionEnum = new HashMap<>();
    private static Map<String, NodeRepetitionEnum> nameRepetitionEnum = new HashMap<>();

    static {
        NodeRepetitionEnum[] repetitionEnums = NodeRepetitionEnum.class.getEnumConstants();
        for (NodeRepetitionEnum repetitionEnum : repetitionEnums) {
            nodeRepetitionEnum.put(repetitionEnum.status, repetitionEnum);
            nameRepetitionEnum.put(repetitionEnum.name(), repetitionEnum);
        }
    }

    NodeRepetitionEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public static NodeRepetitionEnum getByStatus(Integer status) {
        return nodeRepetitionEnum.get(status);
    }

    public static NodeRepetitionEnum getByName(String name) {
        return nameRepetitionEnum.get(name);
    }
}
