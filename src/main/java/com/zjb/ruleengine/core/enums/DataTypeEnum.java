
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据类型〉
 *
 * @author v-zhaojingbo.ea
 * @create 2019/6/25
 * @since 1.0.0
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

    public static DataTypeEnum getDataTypeByName(String name) {

        return dataTypeMap.get(name);
    }


}