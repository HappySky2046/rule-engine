package com.zjb.ruleengine.core.function;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author 赵静波
 * @date 2020-12-07 11:09:34
 */
public abstract class HttpFunction<T, R> extends Function<T, R> {
    private static final Logger log = LogManager.getLogger();

    @Override
    public R execute(T param) {
        return post(param);
    }

    /**
     * 只支持请求参数为json的
     *
     * @param param
     * @return
     */
    protected R post(T param) {
        final String paramStr = getParam(param);
        final String url = getUrl();
        log.debug("接口url {},请求参数 {}", url, paramStr);
        final String result = HttpUtil.post(url, paramStr);
        log.debug("接口url {},请求参数 {}，返回结果 {}", url, paramStr, result);
        return parseObject(result);


    }

    protected R parseObject(String result) {
        final Class resultType = getResultClass();
        try {
            return (R) OBJECT_MAPPER.readValue(result, resultType);
        } catch (IOException e) {
            throw new RuleExecuteException("{}" + e);
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
