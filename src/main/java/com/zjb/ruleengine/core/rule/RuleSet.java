package com.zjb.ruleengine.core.rule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.*;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.Execute;
import com.zjb.ruleengine.core.auth.IAuthorization;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.exception.RuleAuthException;
import com.zjb.ruleengine.core.exception.RuleValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 规则集接口
 *
 * @author 46133
 */
public class RuleSet implements Execute {

    private static final Logger log = LogManager.getLogger();

    /**
     * 普通规则列表，有序
     **/
    private List<AbstractRule> rules;

    private List<AbstractRule> buildRules;

    /**
     * 权限认证
     */
    private List<IAuthorization> authorizations;

    private String id;

    private RuleSetExecutePolicyEnum policy;

    public RuleSet(Builder builder) {
        if (CollectionUtil.isEmpty(builder.rules)) {
            throw new RuleValidationException("rule‘s size not empty");
        }
        this.id = builder.id;
        this.rules = builder.rules;
        if (CollUtil.isEmpty(builder.authorizations)) {
            this.authorizations = Collections.EMPTY_LIST;
        } else {
            this.authorizations = builder.authorizations;

        }
        if (builder.policy == null) {
            this.policy = RuleSetExecutePolicyEnum.ONE;
        } else {
            this.policy = builder.policy;
        }
        build();

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


    public void build() {
        this.buildRules = rules.stream().sorted(Comparator.comparing(AbstractRule::getWeight)).collect(Collectors.toList());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final class Builder {
        /**
         * 普通规则列表，有序
         **/
        protected List<AbstractRule> rules;
        /**
         * 权限认证
         */
        private List<IAuthorization> authorizations;


        private RuleSetExecutePolicyEnum policy;
        private String id;

        public Builder() {

        }

        Builder(RuleSet ruleSet) {
            this.rules = ruleSet.rules;
            this.authorizations = ruleSet.authorizations;
            this.id = ruleSet.id;
            this.policy = ruleSet.policy;
        }

        public Builder rules(List<AbstractRule> rules) {
            this.rules = rules;
            return this;
        }

        public Builder authorizations(List<IAuthorization> authorizations) {
            this.authorizations = authorizations;
            return this;
        }


        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ruleSetExecutePolicyEnum(RuleSetExecutePolicyEnum policy) {
            this.policy = policy;
            return this;
        }

        public RuleSet build() {
            return new RuleSet(this);
        }
    }

    public List<AbstractRule> getRules() {
        final ImmutableList.Builder<AbstractRule> builder = ImmutableList.builder();
        builder.addAll(this.rules);
        return builder.build();
    }

}
