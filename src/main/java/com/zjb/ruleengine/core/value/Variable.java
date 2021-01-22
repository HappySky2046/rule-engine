package com.zjb.ruleengine.core.value;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import com.zjb.ruleengine.core.function.Function;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author 赵静波
 * @date 2020-09-30 16:15:29
 */
public class Variable extends Value {

    private static final Logger log = LogManager.getLogger();
    /**
     * 变量的方法
     */
    private VariableFunction function;

    //private FunctionHolder functionHolder;

    public Variable(VariableFunction function) {
        super();
        Validate.notNull(function);
        this.function = function;
    }

    @Override
    public DataTypeEnum getDataType() {
        final Class resultClass = function.getFunctionHolder().getFunction(this.function.getFunctionName()).getResultClass();
        return DataTypeEnum.getDataTypeByClass(resultClass);
    }

    @Override
    public Collection<Element> collectParameter() {
        final Function function = this.function.getFunctionHolder().getFunction(this.function.getFunctionName());
        final List<Function.Parameter> paramters = function.getParamters();
        return paramters.stream().map(par -> new Element(par.getDataTypeEnum(), par.getName())).collect(Collectors.toSet());
    }


    @Override
    public int getWeight() {
        return LOW;
    }


    @Override
    public Object getValue(Context context) {
        final Map<String, ? extends Value> varParameter = this.function.getParameter();
        final HashMap<String, Object> functionParameter = Maps.newHashMap();
        for (Map.Entry<String, ? extends Value> entry : varParameter.entrySet()) {
            final Object value = entry.getValue().getValue(context);
            functionParameter.put(entry.getKey(), value);
        }
        final Function function = this.function.getFunctionHolder().getFunction(this.function.getFunctionName());
        try {
            final Parameter[] parameters = this.function.getMethodParameters();
            Object[] executeParam = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                final String paramName = parameters[i].getName();
                executeParam[i] = dataConversion(functionParameter.get(paramName), varParameter.get(paramName).getDataType());
            }
            return this.function.getMethod().invoke(function, executeParam);
        } catch (Exception e) {
            throw new RuleExecuteException(e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Variable)) {
            return false;
        }
        Variable variable = (Variable) other;

        return Objects.equals(this.function, variable.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.function);
    }

    @Override
    public String toString() {
        return function.getFunctionName() + "," + getId();
    }
}
