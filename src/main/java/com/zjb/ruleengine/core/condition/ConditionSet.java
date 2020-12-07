
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
public class ConditionSet extends AbstractCondition {

    private static final Logger log = LogManager.getLogger();

    /**
     * 条件集由多个条件组组成
     * 条件全部为true则为true
     **/

    private List<ConditionGroup> conditionGroups;

    public ConditionSet(String id) {
        super(id);
    }

    public ConditionSet(String id, List<ConditionGroup> conditionGroups) {
        super(id);
        this.conditionGroups = conditionGroups;
    }

    public void addConditionGroup(ConditionGroup conditionGroup) {
        if (conditionGroups == null) {
            conditionGroups = new ArrayList<>(2);
        }
        conditionGroups.add(conditionGroup);
    }

    @Override
    public Collection<Element> collectParameter() {
        return conditionGroups.stream().flatMap(con->con.collectParameter().stream()).collect(Collectors.toSet());
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
        for (AbstractCondition condition : conditionGroups) {
            if (!condition.evaluate(context)) {
                result = false;
                break;
            }
        }
        log.debug("ConditionSet id:{},执行结果{}",super.getId(),result);
        return result;

    }

    @Override
    public AbstractCondition build() {
        List<ConditionGroup> collect = conditionGroups.stream().sorted(Comparator.comparing(ConditionGroup::getWeight)).collect(Collectors.toList());
        return new ConditionSet(getId(), collect);
    }

    @Override
    public int getWeight() {
        return conditionGroups.stream().mapToInt(ConditionGroup::getWeight).sum();
    }

    public List<ConditionGroup> getConditionGroups() {
        return Collections.unmodifiableList(conditionGroups);
    }
}