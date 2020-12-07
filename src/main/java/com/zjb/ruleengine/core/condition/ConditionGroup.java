
package com.zjb.ruleengine.core.condition;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.value.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 条件集
 *
 * @author zjb
 * @Date 06/24/2019
 **/

public class ConditionGroup extends AbstractCondition {
    private static final Logger log = LogManager.getLogger();

    /**
     * 条件组，由多个条件组成，条件全部为true则为true
     */
    private List<AbstractCondition> conditions;

    public ConditionGroup(String id) {
        super(id);
    }

    public ConditionGroup(String id, List<AbstractCondition> conditions) {
        super(id);
        this.conditions = conditions;
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
        conditions.add(condition);
    }

    @Override
    public Collection<Element> collectParameter() {
        return conditions.stream().flatMap(con->con.collectParameter().stream()).collect(Collectors.toSet());
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
        log.debug("ConditionGroup id:{},执行结果{}",super.getId(),result);
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