package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ReflectUtil;
import com.zjb.ruleengine.core.Context;
import org.apache.commons.lang3.Validate;

/**
 * @author 赵静波
 * @date 2020-12-07 11:47:03
 */
public class GetPropertyFunction extends Function<GetPropertyFunction.GetPropertyFunctionParameter, Object> {
    @Override
    public Object execute(Context context, GetPropertyFunctionParameter param) {

        return ReflectUtil.getFieldValue(param.object, param.fieldName);
    }

    public static class GetPropertyFunctionParameter {
        /**
         * 对象
         */
        private Object object;
        /**
         * 对象的fieldName
         */
        private String fieldName;


        public GetPropertyFunctionParameter(Object object, String propertyName) {
            Validate.notNull(object);
            Validate.notBlank(propertyName);
            this.object = object;
            this.fieldName = propertyName;
        }


    }


}
