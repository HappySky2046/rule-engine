package com.zjb.ruleengine.core.value;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.google.common.collect.Maps;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import com.zjb.ruleengine.core.function.Function;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
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
        final Function function = this.function.getFunctionHolder().getFunction(this.function.getFunctionName());
        //只运行当前函数需要的参数，防止循环依赖
        final Set<String> funParamNames = (Set<String>) function.getParamters().stream().map(par -> ((Function.Parameter) par).getName()).collect(Collectors.toSet());
        final HashMap<String, Object> functionParameter = Maps.newHashMap();
        if (CollUtil.isNotEmpty(varParameter)) {
            for (Map.Entry<String, ? extends Value> entry : varParameter.entrySet()) {
                if (funParamNames.contains(entry.getKey())) {
                    final Object value = entry.getValue().getValue(context);
                    functionParameter.put(entry.getKey(), value);
                }
            }
        }

        final Parameter parameter = this.function.getMethodParameter();
        final DataTypeEnum dataTypeByClass = DataTypeEnum.getDataTypeByClass(function.getParameterClass());
        try {
            Object executeParam;
            if (dataTypeByClass == DataTypeEnum.POJO) {
                executeParam = function.getParameterClass().newInstance();
                final Field[] declaredFields = function.getParameterClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    declaredField.set(executeParam, functionParameter.get(declaredField.getName()));
                    declaredField.setAccessible(false);
                }
            } else {
                if (functionParameter.containsKey(parameter.getName())) {
                    executeParam = dataConversion(functionParameter.get(parameter.getName()), dataTypeByClass);
                } else {
                    executeParam = null;
                }
            }
            return this.function.getMethod().invoke(function, executeParam);
        } catch (Exception e) {
            log.error("{}", e);
            throw new RuleExecuteException(e);
        }
    }

    public Object dataConversion(Object value, DataTypeEnum dataType) {
        if (Objects.isNull(value) || dataType == null) {
            return null;
        }
        final Class clazz = dataType.getClazz();
        if (dataType != DataTypeEnum.POJO && clazz.isAssignableFrom(value.getClass())) {
            return value;
        }
        final Object o;
        try {
            o = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(value), clazz);
            return o;
        } catch (IOException e) {
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
