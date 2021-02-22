package com.zjb.ruleengine.core;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.IdUtil;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import com.zjb.ruleengine.core.function.Function;
import com.zjb.ruleengine.core.rule.AbstractRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵静波
 * @date 2020-09-30 16:24:20
 */
public class DefaultRuleEngine implements RuleEngine, Serializable {
    private static final long serialVersionUID = 3663237205890586818L;

    private static final Logger log = LogManager.getLogger();
    private ConcurrentHashMap<String, AbstractRule> ruleMap = new ConcurrentHashMap<>();
    private FunctionHolder functionHolder = new FunctionHolder();

    public DefaultRuleEngine() {
        //final Set<Class<?>> classes = ClassUtil.scanPackage(DefaultRuleEngine.class.getPackage().getName(), clazz -> Function.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()));
        final Set<Class<?>> classes = ClassUtil.scanPackage("com.zjb.ruleengine", clazz -> Function.class.isAssignableFrom(clazz) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()));
        try {
            for (Class<?> funClass : classes) {
                if (! ClassUtil.isPublic(funClass)){
                    log.warn("function ：{} 不是public的,不能被注册", funClass.getName());
                }else{
                    this.registerFunction((Function) funClass.newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Author zjb
     * @Description 逐级execute各个规则/条件/动作
     * @Date 08:30 2019-06-24
     * @Param [context, ruleSetCode]
     **/
    @Override
    public Object execute(String ruleId, Context context) {
        MDC.put("requestId", IdUtil.fastSimpleUUID());
        AbstractRule rule = getRule(ruleId);
        if (rule == null) {
            throw new RuleEngineException(String.format("not found %s rule", ruleId));
        }
        log.debug("开始执行{}", ruleId);
        final Object result = rule.execute(context);
        MDC.clear();
        return result;
    }

    @Override
    public void addRule(AbstractRule rule) {
        ruleMap.put(rule.getId(), rule);
    }

    public void registerFunction(Function function) {
        functionHolder.registerFunction(function);
    }

    public Function getFunctionBean(String functionName) {
        return functionHolder.getFunction(functionName);
    }


    public FunctionHolder getFunctionHolder() {
        return functionHolder;
    }

    public void setFunctionHolder(FunctionHolder functionHolder) {
        this.functionHolder = functionHolder;
    }

    @Override
    public void removeRule(String ruleSetId) {
        if (ruleMap.containsKey(ruleSetId)) {
            ruleMap.remove(ruleSetId);
        }
    }

    @Override
    public AbstractRule getRule(String ruleId) {
        return ruleMap.get(ruleId);
    }
}
