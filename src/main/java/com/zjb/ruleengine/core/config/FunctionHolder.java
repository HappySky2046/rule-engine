package com.zjb.ruleengine.core.config;

import com.google.common.collect.Maps;
import com.zjb.ruleengine.core.exception.RuleLoadException;
import com.zjb.ruleengine.core.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-12-01 14:12:15
 */
public class FunctionHolder {
    private static final Logger log = LogManager.getLogger();
    private Map<String, Function> functions = Maps.newHashMap();

    /**
     * 注册function
     *
     * @param function
     */
    public void registerFunction(Function function) {
        final String functionName = getFunctionName(function);
        if (!containFunction(function)) {
            functions.put(functionName, function);
            return;
        }
        log.warn("Function:{} Already exists and will be replace", functionName);
        functions.put(functionName, function);
    }

    /**
     * 是否包含function
     *
     * @param function
     * @return
     */
    public boolean containFunction(Function function) {
        return functions.containsKey(getFunctionName(function));
    }

    public boolean containFunction(String functionName) {
        return functions.containsKey(functionName);
    }

    /**
     * 获取function
     *
     * @param functionName
     * @return
     */
    public Function getFunction(String functionName) {
        return functions.get(functionName);
    }

    public String getFunctionName(Function function) {
        if (Objects.isNull(function)) {
            throw new RuleLoadException("function is not null");
        }
        return function.getName();
    }
    //
    //private FunctionBean parseExecuteType(Function function) {
    //    final Class<? extends Function> functionClass = function.getClass();
    //    Type type = functionClass.getGenericSuperclass();
    //    while (!(type instanceof ParameterizedType)) {
    //        type = ((Class) type).getGenericSuperclass();
    //    }
    //    final ParameterizedType parameterizedType = (ParameterizedType) type;
    //    if (parameterizedType.getRawType() != Function.class) {
    //        throw new RuleEngineException("not found function's generics");
    //    }
    //    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    //    Class executeParamType = (Class) actualTypeArguments[0];
    //    Class executeResultType = (Class) actualTypeArguments[1];
    //    return new FunctionBean(executeParamType, executeResultType, function);
    //}


    public Map<String, Function> getFunctions() {
        return Collections.unmodifiableMap(functions);
    }

}
