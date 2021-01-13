package com.zjb.ruleengine.core.rule;

import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Collectors;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.Execute;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.config.PostProcessor;
import com.zjb.ruleengine.core.config.PreProcessor;
import com.zjb.ruleengine.core.Weight;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;


/**
 * @author 赵静波
 * @date 2020-09-30 15:29:06
 */
public abstract class AbstractRule implements Execute, Weight, Collectors, Serializable {

    private static final Logger log = LogManager.getLogger();
    private static final long serialVersionUID = 6727628276386208126L;

    /**
     * 规则id
     */
    private String id;
    /**
     * 规则条件
     */
    private AbstractCondition condition;
    /**
     * 动作，如果condition==true,则执行actionValue并返回
     */
    private Value action;
    /**
     * 后置处理器
     */
    private List<PostProcessor> postProcessors;
    /**
     * 前置处理器
     */
    private List<PreProcessor> preProcessors;


    public AbstractRule(String id, AbstractCondition condition, Value action) {
        Validate.notBlank(id, "id不能为空");
        this.id = id;
        this.condition = condition;
        this.action = action;
        this.build();
    }




    @Override
    public Collection<Element> collectParameter() {
        final HashSet<Element> parameterNames = Sets.newHashSet();
        parameterNames.addAll(condition.collectParameter());
        parameterNames.addAll(action.collectParameter());
        return parameterNames;
    }

    /**
     * 增加后置处理器
     *
     * @param postProcessor
     */
    public void addPostProcessor(PostProcessor postProcessor) {
        if (postProcessors == null) {
            postProcessors = new ArrayList<>(8);
        }
        postProcessors.add(postProcessor);
    }

    /**
     * 增加前置处理器
     *
     * @param preProcessor
     */
    public void addPreProcessor(PreProcessor preProcessor) {
        if (preProcessors == null) {
            preProcessors = new ArrayList<>(8);
        }
        preProcessors.add(preProcessor);
    }

    @Override
    public Object execute(Context context) {
        log.debug("开始执行规则：{}", id);
        Object result;
        boolean conditionResult = executeCondition(context);
        if (Objects.nonNull(postProcessors)) {
            postProcessors.forEach(postProcessor ->
                    postProcessor.postProcessorBeforeActionExecute(this, context));
        }

        if (conditionResult) {
            result = action.getValue(context);
        } else {
            result = RuleResultEnum.NULL;
        }
        if (Objects.nonNull(postProcessors)) {
            for (PostProcessor postProcessor : postProcessors) {
                result = postProcessor.afterProcessorBeforeActionExecute(this, context, result);
            }
        }
        log.debug("规则:{} 执行结果：{}", this.getId(), result);
        return result;
    }

    protected abstract Boolean executeCondition(Context context);

    public AbstractCondition getCondition() {
        return condition;
    }

    @Override
    public int getWeight() {
        return this.condition.getWeight();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public Value getAction() {
        return action;
    }


    /**
     * 编译规则
     */
    protected  void build(){

    };

}
