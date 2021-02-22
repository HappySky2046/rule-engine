package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * @author 赵静波
 * @date 2020-12-07 11:47:03
 */
public class GetObjectPropertyFunction extends Function<GetObjectPropertyFunction.FunctionParameter, Object> {
    @Override
    public Object execute(FunctionParameter param) {

        final Object object = Objects.requireNonNull(param.object, "object 不能为null");
        final String fieldName = Objects.requireNonNull(param.fieldName, "fieldName不能为null");
        return ReflectUtil.getFieldValue(object, fieldName);
    }

    public static class FunctionParameter {

        /**
         * 对象
         */
        private Object object;
        /**
         * 对象的fieldName
         */
        private String fieldName;


        public FunctionParameter(Object object, String propertyName) {
            Validate.notNull(object);
            Validate.notBlank(propertyName);
            this.object = object;
            this.fieldName = propertyName;
        }
        public FunctionParameter() {
        }

    }


}
