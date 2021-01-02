package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.DefaultRuleEngine;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.rule.Rule;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import org.junit.Test;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2021-01-02
 */
public class SimpleRule extends BaseTest {

    private static String rule_id = "simple_rule";
    private static String element_code = "age";

    /**
     * 规则测试类
     */
    @Test
    public void test() throws Exception {
        DefaultRuleEngine ruleEngine = new DefaultRuleEngine();

        ruleEngine.addRule(getRule());
        final BaseContextImpl context = new BaseContextImpl();
        context.put(element_code, 18);
        final Object simple_rule1 = ruleEngine.execute(rule_id, context);
        System.out.println(simple_rule1);

    }


    public AbstractRule getRule() {
        final DefaultCondition adult = new DefaultCondition("", new Element(DataTypeEnum.NUMBER, element_code), Symbol.ge, new Constant(DataTypeEnum.NUMBER, 18));
        return new Rule(rule_id, adult, new Constant(DataTypeEnum.BOOLEAN, true));
    }


}
