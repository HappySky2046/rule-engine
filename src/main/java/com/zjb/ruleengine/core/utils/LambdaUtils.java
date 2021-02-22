
package com.zjb.ruleengine.core.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * 〈一句话功能简述〉<br>
 * 〈lambda操作工具类〉
 *
 * @author v-dingqianwen.ea
 * @create 2019/9/12
 * @since 1.0.0
 */
public class LambdaUtils {
    private static final Logger log = LogManager.getLogger();
    /**
     * 根据方法引用,获取引用的方法名称,去get/is,首字母小写
     *
     * @return key
     */
    public static <T, R> String getKey(SFunction<T, R> func) {
        String fieldName = convertToFieldName(func);
        return fieldName;

    }

    public static <T, R> String convertToFieldName(SFunction<T, R> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            log.warn("无效的getter方法: " + methodName);
        }
        return StrUtil.lowerFirst(methodName.replace(prefix, ""));
    }

    private static SerializedLambda getSerializedLambda(Serializable fn) {
        //先检查缓存中是否已存在
        SerializedLambda lambda = null;
        try {//提取SerializedLambda并缓存
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            lambda = (SerializedLambda) method.invoke(fn);
        } catch (Exception e) {
            log.error("获取SerializedLambda异常, class=" + fn.getClass().getSimpleName(), e);
        }
        return lambda;
    }


}