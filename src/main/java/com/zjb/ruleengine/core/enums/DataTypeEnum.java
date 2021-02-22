
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
    NUMBER(Number.class), STRING(String.class), COLLECTION(Collection.class), BOOLEAN(Boolean.class), OBJECT(Object.class),
    ;

    //private static Map<String, DataTypeEnum> dataTypeMap = new HashMap<>();
    private static Map<Class, DataTypeEnum> map = new HashMap<>();
    private Class clazz;

    static {
        DataTypeEnum[] constants = DataTypeEnum.class.getEnumConstants();
        for (DataTypeEnum constant : constants) {
            //dataTypeMap.put(constant.name(), constant);
            map.put(constant.clazz, constant);
        }
    }

    DataTypeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static DataTypeEnum getDataTypeByClass(Class name) {

        return map.get(name);
    }



}