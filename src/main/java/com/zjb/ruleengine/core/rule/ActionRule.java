package com.zjb.ruleengine.core.rule;

import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;

import java.util.Collection;

/**
 * @author 赵静波
 * Created on 2021-01-02
 * 没有条件的规则，规则永远执行action并返回结果
 */
public class ActionRule extends AbstractRule {
    @Override
    public Boolean executeCondition(Context context) {
        return true;
    }

    public ActionRule(String id, Value action) {
        super(id, null, action);
    }


    @Override
    public int getWeight() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Collection<Element> collectParameter() {
        return Lists.newArrayList();
    }
}
