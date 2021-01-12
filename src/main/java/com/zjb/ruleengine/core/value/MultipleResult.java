package com.zjb.ruleengine.core.value;

import com.zjb.ruleengine.core.Context;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author 赵静波
 * @date 2020-08-28 16:55:38
 */
public class MultipleResult extends Value {

    private static final Logger log = LogManager.getLogger();
    private Map<String, Value> multipleResult;

    public MultipleResult(Map<String, Value> multipleResult) {
        super();
        Validate.notNull(multipleResult);
        this.multipleResult = multipleResult;

    }

    @Override
    public Class getValueType() {
        return Map.class;
    }

    @Override
    public Collection<Element> collectParameter() {
        return multipleResult.values().stream().flatMap(value -> value.collectParameter().stream()).collect(Collectors.toSet());
    }

    @Override
    public int getWeight() {
        return multipleResult.values().stream().mapToInt(Value::getWeight).sum();
    }

    @Override
    public Map<String, Object> getValue(Context context) {
        Map<String, Object> result = new HashMap<>(multipleResult.size(), 1);
        for (Map.Entry<String, Value> entry : multipleResult.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getValue(context));
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MultipleResult)) {
            return false;
        }
        MultipleResult variable = (MultipleResult) other;
        //比较execute
        if (this.multipleResult.equals(variable.multipleResult)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.multipleResult);
    }

    @Override
    public String toString() {
        return multipleResult.entrySet().stream().map(entry->entry.getKey()+"="+entry.getValue()).collect(Collectors.joining());
    }
}
