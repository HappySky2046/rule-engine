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
public abstract class HttpJsonObjectFunction<T> extends Function<T, JsonNode> {
    private static final Logger log = LogManager.getLogger();

    @Override
    public JsonNode execute(T param) {
        return post(param);
    }

    /**
     * 只支持请求参数为json的
     *
     * @param param
     * @return
     */
    protected JsonNode post(T param) {
        try {

            final String url = getUrl();
            final String paramStr = getParam(param);
            log.debug("接口url {},请求参数 {}", url, paramStr);
            final String result = HttpUtil.post(url, getParam(param));
            log.debug("接口url {},请求参数 {}，返回结果 {}", url, paramStr, result);
            return new ObjectMapper().readTree(result);
        } catch (Exception e) {

            log.error("{}", e);
            throw new RuleExecuteException("接口返回值parse错误{}", e);
        }

    }


    protected String getParam(T param) {
        try {
            return OBJECT_MAPPER.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            throw new RuleExecuteException(e);
        }
    }

    protected abstract String getUrl();

}
