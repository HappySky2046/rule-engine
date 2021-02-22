package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-12-07 11:47:03
 */
public class GetJsonPropertyFunction extends Function<GetJsonPropertyFunction.GetPropertyFunctionParameter, Object> {
    @Override
    public Object execute(GetPropertyFunctionParameter param) {
        return param.jsonNode.findValue(param.fieldName);

    }

    public static class GetPropertyFunctionParameter {
        /**
         * 对象
         */
        private JsonNode jsonNode;
        /**
         * 对象的fieldName
         */
        private String fieldName;


        public GetPropertyFunctionParameter(JsonNode jsonNode, String propertyName) {
            Validate.notNull(jsonNode);
            Validate.notBlank(propertyName);
            this.jsonNode = jsonNode;
            this.fieldName = propertyName;
        }


    }


}
