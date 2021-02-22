package com.zjb.ruleengine.core.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.auth.IAuthorization;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.exception.RuleAuthException;
import com.zjb.ruleengine.core.value.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 规则集接口
 *
 * @author 46133
 */
public class RuleSet extends AbstractRule {

    private static final Logger log = LogManager.getLogger();

    /**
     * 普通规则列表，有序
     **/
    private List<AbstractRule> rules;
    /**
     * 权限认证
     */
    private List<IAuthorization> authorizations;


    private RuleSetExecutePolicyEnum policy;

    public RuleSet(String id, AbstractCondition condition, Value action, List<AbstractRule> rules, List<IAuthorization> authorizations, RuleSetExecutePolicyEnum policy) {
        super(id, condition, action);
        this.rules = rules;
        this.authorizations = authorizations;
        this.policy = policy;
    }

    @Override
    public Boolean executeCondition(Context context) {
        return null;
    }

    @Override
    public Object execute(Context context) {
        //校验权限
        log.debug("规则集：{}开始执行", this.getId());
        authorizations.stream()
                .filter(auth -> !auth.authorization(context))
                .findFirst()
                .ifPresent(auth -> {
                    throw new RuleAuthException(auth.getId() + "认证未通过");
                });
        //执行规则
        switch (policy) {
            case ANY:
                Collections.shuffle(rules);
                for (AbstractRule rule : rules) {
                    Object execute = rule.execute(context);
                    if (execute != RuleResultEnum.NULL) {
                        return execute;
                    }
                }
                break;
            case ONE:
                for (AbstractRule rule : rules) {
                    Object execute = rule.execute(context);
                    if (execute != RuleResultEnum.NULL) {
                        return execute;
                    }
                }
                break;
            case ALL:
                List<Object> result = new ArrayList<>();
                for (AbstractRule rule : rules) {
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

    /**
     * 转换为 json string
     *
     * @return json
     */
    public String toJsonString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * json string 转换为ruleSet
     *
     * @param json
     * @return
     */
    public void parse(String json, RuleSetParser parser) {
        parser.parse(json, this);
    }


    public List<AbstractRule> getRules() {
        return rules;
    }

    @Override
    public void build() {

    }
}
