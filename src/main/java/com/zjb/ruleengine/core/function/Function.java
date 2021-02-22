package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ClassUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleCompileException;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import com.zjb.ruleengine.core.exception.RuleValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-09-30 16:15:29
 */
public abstract class Function<T, R> implements Serializable {
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = -6994254036155752759L;

    //private Class parameterClass;
    //private Class resultClass;

    public Function() {
        //parseExecuteType();
    }

    /**
     * @param param 参数的map
     * @return
     */
    public abstract R execute(T param);


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Function)) {
            return false;
        }
        Function function = (Function) other;
        if (getParameterClass() != function.getParameterClass()) {
            return false;
        }
        if (getResultClass() != function.getResultClass()) {
            return false;
        }
        return true;
    }

    public Class getResultClass() {

        final Type[] actualTypeArguments = getType();
        if (actualTypeArguments[1] instanceof Class) {
            return (Class) actualTypeArguments[1];
        }
        return Object.class;

    }

    private Type[] getType() {
        final Class<? extends Function> functionClass = this.getClass();
        if (functionClass.isAnonymousClass()) {
            throw new RuleCompileException("匿名内部部需要重写当前方法");
        }
        Type type = functionClass.getGenericSuperclass();
        while (!(type instanceof ParameterizedType) && ((ParameterizedType) type).getRawType() != Function.class) {
            throw new RuleEngineException("not found function's generics");
        }

        final ParameterizedType parameterizedType = (ParameterizedType) type;

        return parameterizedType.getActualTypeArguments();
    }


    public Class getParameterClass() {
        final Type[] actualTypeArguments = getType();
        if (actualTypeArguments[0] instanceof Class) {
            return (Class) actualTypeArguments[0];
        }
        return Object.class;

    }

    public String getExecuteMethodName() {
        return "execute";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    //protected void parseExecuteType() {
    //    final Class<? extends Function> functionClass = this.getClass();
    //    Type type = functionClass.getGenericSuperclass();
    //    while (!(type instanceof ParameterizedType) && ((ParameterizedType) type).getRawType() != Function.class) {
    //        throw new RuleEngineException("not found function's generics");
    //    }
    //
    //    final ParameterizedType parameterizedType = (ParameterizedType) type;
    //
    //    final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
    //    if (actualTypeArguments[0] instanceof Class) {
    //        parameterClass = (Class) actualTypeArguments[0];
    //    } else {
    //        parameterClass = Object.class;
    //    }
    //    if (actualTypeArguments[1] instanceof Class) {
    //        resultClass = (Class) actualTypeArguments[1];
    //    } else {
    //        resultClass = Object.class;
    //    }
    //
    //}


    /**
     * @return
     */
    public List<Parameter> getParamters() {
        final Class parameterClass = getParameterClass();
        final DataTypeEnum dataTypeByClass = DataTypeEnum.getDataTypeByClass(parameterClass);

        if (dataTypeByClass != DataTypeEnum.POJO && dataTypeByClass != DataTypeEnum.JSONOBJECT) {
            try {
                final String name = ClassUtil.getDeclaredMethod(this.getClass(), getExecuteMethodName(), parameterClass).getParameters()[0].getName();
                return Lists.newArrayList(new Parameter(dataTypeByClass, name));
            } catch (Exception e) {
                throw new RuleValidationException(e);
            }
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
