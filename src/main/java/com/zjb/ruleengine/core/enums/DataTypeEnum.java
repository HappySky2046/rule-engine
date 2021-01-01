
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */
public enum DataTypeEnum implements Serializable {
    NUMBER(Number.class), STRING(String.class), COLLECTION(Collection.class), BOOLEAN(Boolean.class),;

    private static Map<String, DataTypeEnum> dataTypeMap = new HashMap<>();
    private Class clazz;

    static {
        DataTypeEnum[] constants = DataTypeEnum.class.getEnumConstants();
        for (DataTypeEnum constant : constants) {
            dataTypeMap.put(constant.name(), constant);
        }
    }

    DataTypeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static DataTypeEnum getDataTypeByName(String name) {

        return dataTypeMap.get(name);
    }


}