package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;

/**
 * @author 赵静波
 * @date 2020-12-07 11:47:03
 */
public class GetJsonPropertyFunction extends Function<GetJsonPropertyFunction.FunctionParameter, Object> {
    @Override
    public Object execute(FunctionParameter param) {
        final String[] split = param.fieldName.split("\\.");
        JsonNode jsonNode = param.jsonNode;
        for (String name : split) {
            jsonNode = jsonNode.get(name);
        }
        return jsonNode;
    }

    public static class FunctionParameter {
        /**
         * 对象
         */
        private JsonNode jsonNode;
        /**
         * 对象的fieldName
         */
        private String fieldName;


        public FunctionParameter(JsonNode jsonNode, String propertyName) {
            Validate.notNull(jsonNode);
            Validate.notBlank(propertyName);
            this.jsonNode = jsonNode;
            this.fieldName = propertyName;
        }

        public FunctionParameter() {

        }

    }



}
