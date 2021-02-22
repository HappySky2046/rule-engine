package com.zjb.ruleengine.core.rule;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.value.Value;

/**
 * @author 赵静波
 * Created on 2021-01-02
 * 没有条件的规则，规则永远执行action并返回结果
 */
public class DefaultRule extends AbstractRule {
    @Override
    public Boolean executeCondition(Context context) {
        return true;
    }

    public DefaultRule(String id,  Value action) {
        super(id, null, action);
    }

    @Override
    public void build() {

    }
}
