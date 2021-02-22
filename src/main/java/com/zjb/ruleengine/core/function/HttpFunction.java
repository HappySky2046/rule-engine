package com.zjb.ruleengine.core.function;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.exception.RuleExecuteException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2020-12-07
 */
public abstract class HttpFunction<T, R> extends Function<T, R> {
    private static final Logger log = LogManager.getLogger();


    @Override
    public R execute(Context context, T param) {
        return post(context, param);
    }

    /**
     * 只支持请求参数为json的
     *
     * @param context
     * @param param
     * @return
     */
    protected R post(Context context, T param) {
        return parseObject(HttpUtil.post(getUrl(), getParam(context, param)));
    }

    protected R parseObject(String result) {
        final Class resultType = getFunctionResultType();
        if (ClassUtil.isSimpleValueType(resultType)) {
            try {
                return (R) resultType.getConstructor(String.class).newInstance(result);
            } catch (Exception e) {
                log.error("{}", e);
            }
        }else {
            try {
                return (R) new ObjectMapper().readValue(result, resultType);
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
        throw new RuleExecuteException("result 不能转换为" + resultType.getSimpleName());
    }

    protected Map<String, Object> getParam(Context context, T param) {

        final ImmutableMap.Builder builder = new ImmutableMap.Builder();
        builder.put("context", context);
        builder.put("param", param);
        return builder.build();

    }

    protected abstract String getUrl();

}
