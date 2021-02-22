
package com.zjb.ruleengine.core.enums;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */
public enum DataTypeEnum implements Serializable {
    NUMBER(Number.class), STRING(String.class), COLLECTION(Collection.class), BOOLEAN(Boolean.class), POJO(Object.class), JSONOBJECT(JsonNode.class);

    private Class clazz;

    DataTypeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static DataTypeEnum getDataTypeByClass(Class clazz) {
        if (Number.class.isAssignableFrom(clazz)) {
            return NUMBER;
        }
        if (STRING.clazz.isAssignableFrom(clazz)) {
            return STRING;
        }
        if (COLLECTION.clazz.isAssignableFrom(clazz)) {
            return STRING;
        }
        if (BOOLEAN.clazz.isAssignableFrom(clazz)) {
            return STRING;
        }
        if (JSONOBJECT.clazz.isAssignableFrom(clazz)) {
            return STRING;
        }
        return POJO;
    }
}