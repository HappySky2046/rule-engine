package com.zjb.ruleengine.core.value;

import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */
public class Element extends Value {

    private static final Logger log = LogManager.getLogger();
    /**
     * 元素code
     */
    private String code;

    /**
     * 数据类型
     */
    private DataTypeEnum dataType;

    public Element(DataTypeEnum dataType, String code) {
        Validate.notBlank(code);
        Validate.notNull(dataType);
        this.dataType = dataType;
        this.code = code;
    }

    @Override
    public Class getValueType() {
        return dataType.getClazz();
    }

    @Override
    public Collection<Element> collectParameter() {
        return Sets.newHashSet(this);
    }

    @Override
    public int getWeight() {
        return 1;
    }

    /**
     * 元素类型
     *
     * @param context 上下文
     * @return 结果从上下文中获取
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(Context context) {
        Object value = context.get(code);
        return dataConversion(value, dataType);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Element)) {
            return false;
        }
        Element element = (Element) other;
        if (this.dataType != element.dataType) {
            return false;
        }
        return this.getCode().equals(element.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code, this.dataType);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.dataType.getClazz().getSimpleName() + " : " + this.code;
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
