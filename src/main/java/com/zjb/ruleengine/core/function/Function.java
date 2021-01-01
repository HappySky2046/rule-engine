package com.zjb.ruleengine.core.function;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-09-30 16:15:29
 */
public abstract class Function<T, R> implements Serializable {

    private static final long serialVersionUID = -6994254036155752759L;

    private Class parameterClass;
    private Class resultClass;

    public Function() {
        parseExecuteType();
    }

    /**
     * @param param 参数的map
     * @return
     */
    public abstract R execute(Context context, T param);


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Function)) {
            return false;
        }

        //Function function = (Function) other;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    protected void parseExecuteType() {
        final Class<? extends Function> functionClass = this.getClass();
        Type type = functionClass.getGenericSuperclass();
        while (!(type instanceof ParameterizedType) && ((ParameterizedType)type).getRawType() != Function.class) {
            throw new RuleEngineException("not found function's generics");
        }


        final ParameterizedType parameterizedType = (ParameterizedType) type;

        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments[0] instanceof TypeVariableImpl) {
            parameterClass = Object.class;
        } else {
            parameterClass = (Class) actualTypeArguments[0];
        }
        if (actualTypeArguments[1] instanceof TypeVariableImpl) {
            resultClass = Object.class;
        } else {
            resultClass = (Class) actualTypeArguments[1];
        }


    }

    public Class getFunctionParamType() {
        return parameterClass;
    }


    public Class getFunctionResultType() {
        return resultClass;
    }
}
