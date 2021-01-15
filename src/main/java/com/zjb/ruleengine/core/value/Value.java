
package com.zjb.ruleengine.core.value;

import com.zjb.ruleengine.core.Collectors;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.Weight;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleValidationException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static com.zjb.ruleengine.core.condition.evaluate.BooleanEvaluateStrategy.FALSE_STRING;
import static com.zjb.ruleengine.core.condition.evaluate.BooleanEvaluateStrategy.TRUE_STRING;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:29
 */

public abstract class Value implements Serializable, Weight, Collectors, AutoExecute {
    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = 8892490391433239102L;
    private String id;

    /**
     * 获取权重，权重会影响到执行的先后顺序，权重越高，执行越延后
     *
     * @return
     */
    @Override
    public abstract int getWeight();

    /**
     * 获取当前value有几个入参
     *
     * @return
     */
    @Override
    public abstract Collection<Element> collectParameter();

    public abstract Object getValue(Context context);

    /**
     * value的类型
     *
     * @return
     */
    public abstract Class getValueType();

    @Override
    public String toString() {
        return id;
    }

    public Object dataConversion(Object value, DataTypeEnum dataType) {
        if (Objects.isNull(value)) {
            return null;
        }
        switch (dataType) {
            case COLLECTION:
                if (value instanceof Collection) {
                    return value;
                }
                return Arrays.asList((value + "").split(","));
            case NUMBER:
                if (value instanceof Number) {
                    return value;
                }
                return NumberUtils.createNumber(value + "");
            case STRING:
                if (value instanceof String) {
                    return value;
                }
                return String.valueOf(value);
            case BOOLEAN:
                if (value instanceof Boolean) {
                    return value;
                }
                if (Objects.equals(value, TRUE_STRING)) {
                    return true;
                }
                if (Objects.equals(value, FALSE_STRING)) {
                    return false;
                }
                throw new RuleValidationException("只能是boolean类型");
            default:
                return value;
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}