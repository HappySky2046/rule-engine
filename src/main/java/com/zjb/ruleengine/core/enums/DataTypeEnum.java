
package com.zjb.ruleengine.core.enums;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */
public enum DataTypeEnum implements Serializable {
    NUMBER(Number.class), STRING(String.class), COLLECTION(Collection.class),
    BOOLEAN(Boolean.class), POJO(Object.class), JSONOBJECT(JsonNode.class),
    OBJECT(Object.class), MAP(Map.class);

    private Class clazz;

    DataTypeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static DataTypeEnum getDataTypeByClass(Class clazz) {
        if (NUMBER.clazz.isAssignableFrom(clazz)) {
            return NUMBER;
        }
        if (STRING.clazz.isAssignableFrom(clazz)) {
            return STRING;
        }
        if (COLLECTION.clazz.isAssignableFrom(clazz)) {
            return COLLECTION;
        }
        if (BOOLEAN.clazz.isAssignableFrom(clazz)) {
            return BOOLEAN;
        }
        if (JSONOBJECT.clazz.isAssignableFrom(clazz)) {
            return JSONOBJECT;
        }
        if (MAP.clazz.isAssignableFrom(clazz)) {
            return MAP;
        }
        if (clazz == OBJECT.clazz) {
            return OBJECT;
        }
        return POJO;
    }

    public static DataTypeEnum getDataTypeByName(String name) {
        final DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(name);
        if (dataTypeEnum != null) {
            return dataTypeEnum;
        }
        return POJO;
    }

}