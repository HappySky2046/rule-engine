package com.zjb.ruleengine.core;

import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.function.Function;
import com.zjb.ruleengine.core.function.FunctionBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 46133
 */
public class DefaultRuleEngine implements RuleEngine, Serializable {
    private static final long serialVersionUID = 3663237205890586818L;

    private static final Logger log = LogManager.getLogger();
    private ConcurrentHashMap<String, AbstractRule> ruleMap = new ConcurrentHashMap<>();
    private FunctionHolder functionHolder = new FunctionHolder();

    /**
     * @return void
     * @Author zjb
     * @Description 逐级execute各个规则/条件/动作
     * @Date 08:30 2019-06-24
     * @Param [context, ruleSetCode]
     **/
    @Override
    public Object execute(String ruleId, Context context) {
        AbstractRule ruleSet = ruleMap.get(ruleId);
        if (ruleSet == null) {
            throw new RuleEngineException(String.format("not found %s rule", ruleId));
        }
        log.debug("开始执行规则集{}", ruleId);
        final Object result = ruleSet.execute(context);
        return result;
    }

    @Override
    public void addRuleSet(AbstractRule rule) {
        ruleMap.put(rule.getId(), rule);
    }

    public void registerFunction(Function function) {
        functionHolder.registerFunction(function);
    }

    public FunctionBean getFunctionBean(String functionName) {
        return functionHolder.getFunction(functionName);
    }


    public FunctionHolder getFunctionHolder() {
        return functionHolder;
    }

    public void setFunctionHolder(FunctionHolder functionHolder) {
        this.functionHolder = functionHolder;
    }

    @Override
    public void removeRuleSet(String ruleSetId) {
        if (ruleMap.containsKey(ruleSetId)) {
            ruleMap.remove(ruleSetId);
        }
    }


}
