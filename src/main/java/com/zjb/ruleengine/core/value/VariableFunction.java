package com.zjb.ruleengine.core.value;

import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-12-01 14:26:36
 */
public class VariableFunction<T> {

    private String functionName;
    /**
     * parameters of function
     * key=参数的key
     */
    private T parameter;

    public VariableFunction(String functionName, T parameter) {

        Validate.notBlank(functionName, "functionName is not blank");
        Validate.notNull(parameter, "parameter is not null");
        this.functionName = functionName;
        this.parameter = parameter;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VariableFunction)) {
            return false;
        }
        final VariableFunction other = (VariableFunction) obj;
        return Objects.equals(other.parameter, this.parameter);
    }
}
