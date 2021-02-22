package com.zjb.ruleengine.core.value;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * 原始变量，execute的返回值是intandceof {@link Number} 或者 {@link String}
 *
 * @author zjb
 * @Date 07/11/2019
 **/
public class Variable extends Value {

    private static final Logger log = LogManager.getLogger();
    /**
     * 变量的方法
     */
    private VariableFunction function;

    private FunctionHolder functionHolder;

    public Variable(VariableFunction function, FunctionHolder functionHolder) {
        super();
        Validate.notNull(function);
        Validate.notNull(functionHolder);
        this.functionHolder = functionHolder;
        if (!functionHolder.containFunction(function.getFunctionName())) {
            final String message = StrFormatter.format("not found function:{}", function.getFunctionName());
            log.error(message);
            throw new RuleEngineException(message);
        }
        final FunctionBean functionBean = functionHolder.getFunction(function.getFunctionName());
        final Class functionParamType = functionBean.getFunctionParamType();
        if (!function.getParameter().getClass().isAssignableFrom(functionParamType)) {
            final String message = StrFormatter.format("function:{} parameter {} not cast to {}", function.getFunctionName(), function.getParameter().getClass().getSimpleName(), functionParamType);
            log.error(message);
            throw new RuleEngineException(message);
        }

        this.function = function;
    }

    @Override
    public Collection<Element> collectParameter() {
        final Object parameter = function.getParameter();
        return recursion(parameter);
    }

    private Set<Element> recursion(Object parameter) {
        final Class<?> parameterClass = parameter.getClass();
        if (ClassUtil.isSimpleValueType(parameterClass)) {
            return Sets.newHashSet();
        }
        final Set<Element> result = Sets.newHashSet();
        if (parameter instanceof Map) {
            final Map parameterMap = (Map) parameter;
            parameterMap.forEach((k, v) -> {
                result.addAll(recursion(k));
                result.addAll(recursion(v));
            });
            return result;
        }
        if (parameter instanceof Collection) {
            ((Collection<?>) parameter).stream().forEach(p -> result.addAll(recursion(p)));
            return result;
        }
        if (parameter instanceof Constant || parameter instanceof Element) {
            result.addAll(((Element) parameter).collectParameter());
            return result;
        }
        if (parameter instanceof Variable) {
            final Variable variable = (Variable) parameter;
            result.addAll(recursion(variable.function.getParameter()));
            return result;
        }
        final Field[] declaredFields = parameterClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                final Object o = declaredField.get(parameter);
                declaredField.setAccessible(false);
                result.addAll(recursion(o));
            } catch (IllegalAccessException e) {
                log.error("{}", e);
                throw new RuleEngineException(e.getMessage());
            }
        }

        return result;
    }

    @Override
    public int getWeight() {
        return 2;
    }


    @Override
    public Object getValue(Context context) {
        final Object parameter = function.getParameter();
        final FunctionBean function = functionHolder.getFunction(this.function.getFunctionName());
        return function.getFunction().execute(context, parameter);
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



}
