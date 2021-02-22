package com.zjb.ruleengine;

import cn.hutool.core.util.ClassUtil;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.function.Function;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * @author 赵静波
 * @date 2020-12-07 14:30:00
 */
public class BaseTest {
    public FunctionHolder registerFunction() throws Exception{
        FunctionHolder holder = new FunctionHolder();
        final Set<Class<?>> classes = ClassUtil.scanPackage("com.zjb.ruleengine", clazz -> Function.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()));
        for (Class<?> aClass : classes) {
            holder.registerFunction((Function) aClass.newInstance());

        }
        return holder;
    }
}
