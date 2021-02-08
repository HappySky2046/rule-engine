package com.zjb.ruleengine.core.function;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-12-07 11:09:34
 */
public abstract class HttpJsonObjectFunction<T> extends HttpFunction<T, JsonNode> {


    @Override
    public JsonNode execute(T param) {
        return post(param);
    }

    @Override
    public Class getResultClass() {
        return JsonNode.class;
    }

    @Override
    protected abstract String getUrl();

}
