package com.zjb.ruleengine.core.function;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-09-30 16:15:29
 */
public abstract class Function<T, R> implements Serializable {
    private static final Logger log = LogManager.getLogger();
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
        while (!(type instanceof ParameterizedType) && ((ParameterizedType) type).getRawType() != Function.class) {
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

    /**
     * @return
     */
    public List<Parameter> listParamters() {
        if (ClassUtil.isSimpleValueType(parameterClass)) {
            final String execute;
            try {
                execute = this.getClass().getMethod("execute", Context.class, Object.class).getParameters()[1].getName();
            } catch (NoSuchMethodException e) {
                log.error("{}", e);
                throw new RuleExecuteException(e.getMessage());
            }
            final DataTypeEnum dataTypeByClass = DataTypeEnum.getDataTypeByClass(parameterClass);
            return Lists.newArrayList(new Parameter(dataTypeByClass, execute));
        }
        final Field[] declaredFields = parameterClass.getDeclaredFields();
        final ArrayList<Parameter> result = Lists.newArrayList();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            final Class<?> type = declaredField.getType();
            result.add(new Parameter(DataTypeEnum.getDataTypeByClass(type), declaredField.getName()));
            declaredField.setAccessible(false);
        }
        return result;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public static class Parameter {
        private DataTypeEnum dataTypeEnum;
        private String name;

        public Parameter() {
        }

        public Parameter(DataTypeEnum dataTypeEnum, String name) {
            this.dataTypeEnum = dataTypeEnum;
            this.name = name;
        }

        public DataTypeEnum getDataTypeEnum() {
            return dataTypeEnum;
        }

        public void setDataTypeEnum(DataTypeEnum dataTypeEnum) {
            this.dataTypeEnum = dataTypeEnum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
