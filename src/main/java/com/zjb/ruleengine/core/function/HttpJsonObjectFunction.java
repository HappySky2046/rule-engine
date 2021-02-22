package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ClassUtil;
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
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode execute(Context context, T param) {
        return post(context, param);
    }

    /**
     * 只支持请求参数为json的
     *
     * @param context
     * @param param
     * @return
     */
    protected JsonNode post(Context context, T param) {
        try {
            final String paramStr = objectMapper.writeValueAsString(getParam(context, param));
            final String url = getUrl();
            final String result = HttpUtil.post(url, objectMapper.writeValueAsString(getParam(context, param)));
            log.debug("接口url {},请求参数 {}，返回结果 {}", url, paramStr, result);
            return new ObjectMapper().readTree(result);
        } catch (Exception e) {

            log.error("{}", e);
            throw new RuleExecuteException("接口返回值parse错误{}", e);
        }

    }


    protected Map<String, Object> getParam(Context context, T param) {

        final ImmutableMap.Builder builder = new ImmutableMap.Builder();
        builder.put("context", context);
        builder.put("param", param);

        return builder.build();

    }

    protected abstract String getUrl();

}
