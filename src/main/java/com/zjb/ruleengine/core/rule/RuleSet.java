package com.zjb.ruleengine.core.rule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.value.Element;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author 赵静波
 * @date 2020-09-30 16:15:29
 */
public class RuleSet extends AbstractRule {

    private static final Logger log = LogManager.getLogger();

    /**
     * 普通规则列表，有序
     **/
    private List<AbstractRule> rules;

    private List<AbstractRule> buildRules;


    private RuleSetExecutePolicyEnum policy = RuleSetExecutePolicyEnum.ONE;

    public RuleSet(String id, List<? extends AbstractRule> rules) {
        super(id, null, null);
        Validate.notEmpty(rules);
        this.rules = Lists.newArrayList(rules);
        this.buildRules = rules.stream().sorted(Comparator.comparing(AbstractRule::getWeight)).collect(Collectors.toList());
    }


    @Override
    public Boolean executeCondition(Context context) {
        return false;
    }

    @Override
    public Collection<Element> collectParameter() {
        return rules.stream().flatMap(rule -> rule.collectParameter().stream()).collect(Collectors.toList());
    }

    @Override
    public Object execute(Context context) {
        //校验权限
        log.debug("规则集：{}开始执行", this.getId());
        //执行规则
        switch (policy) {
            case ANY:
                Collections.shuffle(buildRules);
                for (AbstractRule rule : buildRules) {
                    Object execute = rule.execute(context);
                    if (execute != RuleResultEnum.NULL) {
                        return execute;
                    }
                }
                break;
            case ONE:
                for (AbstractRule rule : buildRules) {
                    Object execute = rule.execute(context);
                    if (execute != RuleResultEnum.NULL) {
                        return execute;
                    }
                }
                break;
            case ALL:
                List<Object> result = new ArrayList<>();
                for (AbstractRule rule : buildRules) {
                    Object execute = rule.execute(context);
                    if (execute != RuleResultEnum.NULL) {
                        result.add(execute);
                    }
                }
                if (!result.isEmpty()) {
                    return result;
                }
                break;
            default:
                return RuleResultEnum.NULL;
        }

        return RuleResultEnum.NULL;
    }

    public RuleSetExecutePolicyEnum getPolicy() {
        return policy;
    }

    public void setPolicy(RuleSetExecutePolicyEnum policy) {
        this.policy = policy;
    }

    public List<AbstractRule> getRules() {
        return ImmutableList.copyOf(this.rules);
    }

}
