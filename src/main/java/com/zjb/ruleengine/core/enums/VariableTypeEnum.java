package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
public enum VariableTypeEnum implements Serializable {
    CONSTANT(0), VARIABLE(1), ELEMENT(2), RESULT(3);
    /**
     * @author 赵静波
     * @date 2020-08-24 19:00:38
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    private static Map<Integer, VariableTypeEnum> map = new HashMap<>();
    static {
        VariableTypeEnum[] enumConstants = VariableTypeEnum.class.getEnumConstants();
        for (VariableTypeEnum enumConstant : enumConstants) {
            map.put(enumConstant.status, enumConstant);
        }
    }
    VariableTypeEnum(Integer status) {
        this.status = status;
    }

    public static VariableTypeEnum getStatus(Integer status) {
        return map.get(status);
    }
}
