package com.zjb.ruleengine.core.value;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ClassUtil;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.exception.RuleValidationException;
import com.zjb.ruleengine.core.function.Function;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-12-01 14:26:36
 */

public class VariableFunction {
    private static final Logger log = LogManager.getLogger();
    private String functionName;
    /**
     * parameters of function
     * key=参数的key
     */
    private Map<String, ? extends Value> parameter;

    private FunctionHolder functionHolder;
    private Method method;
    private Parameter methodParameter;

    public VariableFunction(String functionName, Map<String, ? extends Value> parameter, FunctionHolder functionHolder) {

        Validate.notBlank(functionName, "functionName is not blank");
        Validate.notNull(parameter, "parameter is not null");
        Validate.notNull(functionHolder, "functionHolder is not null");
        if (!functionHolder.containFunction(functionName)) {
            final String message = StrFormatter.format("not found function:{}", functionName);
            log.error(message);
            throw new RuleValidationException(message);
        }
        final Function function = functionHolder.getFunction(functionName);
        final List<Function.Parameter> parameters = function.getParamters();
        for (Function.Parameter funArg : parameters) {
            if (!parameter.containsKey(funArg.getName())) {
                log.warn("没有{}参数", funArg.getName());
                continue;
            }
            final DataTypeEnum dataType = parameter.get(funArg.getName()).getDataType();
            if (dataType != funArg.getDataTypeEnum()) {
                throw new RuleValidationException("参数类型不匹配");
            }
        }

        this.functionName = functionName;
        this.parameter = parameter;
        this.functionHolder = functionHolder;

        try {
            this.method = ClassUtil.getDeclaredMethod(function.getClass(), function.getExecuteMethodName(), function.getParameterClass());
        } catch (Exception e) {
            throw new RuleValidationException(e);
        }
        final Parameter[] methodParameters = this.method.getParameters();
        if (methodParameters.length > 1) {
            throw new RuleValidationException("只支持单个参数");
        }
        this.methodParameter = methodParameters[0];
    }

    public Method getMethod() {
        return method;
    }

    public Parameter getMethodParameter() {
        return methodParameter;
    }

    public String getFunctionName() {
        return functionName;
    }


    public Map<String, ? extends Value> getParameter() {
        return parameter;
    }

    public FunctionHolder getFunctionHolder() {
        return functionHolder;
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
