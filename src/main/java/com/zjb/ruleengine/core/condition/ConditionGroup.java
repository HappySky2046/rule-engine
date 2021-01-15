
package com.zjb.ruleengine.core.condition;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.value.Element;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author 赵静波
 * @date 2020-09-30 14:53:22
 */

public class ConditionGroup extends AbstractCondition {
    private static final Logger log = LogManager.getLogger();
    /**
     * 全局缓存，少存储condition
     */
    private static Map<AbstractCondition, AbstractCondition> CONDITION_CACHE = new ConcurrentHashMap<>();
    /**
     * 条件组，由多个条件组成，条件全部为true则为true
     */
    private List<AbstractCondition> conditions;

    /**
     * 原型模式
     *
     * @param id
     * @param conditions
     */
    public ConditionGroup(String id, List<? extends AbstractCondition> conditions) {
        super(id);
        Validate.notEmpty(conditions, "conditions not empty");
        this.conditions = conditions.stream().map(con -> {
            if (CONDITION_CACHE.containsKey(con)) {
                return CONDITION_CACHE.get(con);
            }
            CONDITION_CACHE.put(con, con);
            return con;
        }).collect(Collectors.toList());
    }

    /**
     * 原型模式
     *
     * @param conditions
     */
    public ConditionGroup(List<? extends AbstractCondition> conditions) {
        this("", conditions);
    }

    /**
     * 增加条件
     *
     * @param condition
     */
    public void addCondition(AbstractCondition condition) {
        if (conditions == null) {
            conditions = new ArrayList<>(16);
        }
        if (CONDITION_CACHE.containsKey(condition)) {
            conditions.add(CONDITION_CACHE.get(condition));
        } else {
            CONDITION_CACHE.put(condition, condition);
            conditions.add(condition);
        }

    }

    @Override
    public Collection<Element> collectParameter() {
        return conditions.stream().flatMap(con -> con.collectParameter().stream()).collect(Collectors.toSet());
    }

    /**
     * @return boolean
     * @Author zjb
     * @Description
     * @Date 10:49 2019-06-24
     * @Param []
     **/
    @Override
    public boolean evaluate(Context context) {
        boolean result = true;
        for (AbstractCondition condition : conditions) {
            if (!condition.evaluate(context)) {
                result = false;
                break;
            }
        }
        log.debug("ConditionGroup id:{},执行结果{}", super.getId(), result);
        return result;
    }

    @Override
    public AbstractCondition build() {
        List<AbstractCondition> collect = conditions.stream().sorted(Comparator.comparing(AbstractCondition::getWeight)).collect(Collectors.toList());
        return new ConditionGroup(super.getId(), collect);
    }

    @Override
    public int getWeight() {
        return conditions.stream().mapToInt(AbstractCondition::getWeight).sum();
    }

    public List<AbstractCondition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }
}