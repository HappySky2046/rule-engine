package com.zjb.ruleengine.rule;

import com.zjb.ruleengine.BaseTest;
import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.DefaultRuleEngine;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.ConditionSet;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.RuleResultEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.rule.Rule;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author 赵静波
 * Created on 2021-01-02
 */
public class RuleOnConditionset extends BaseTest {

    private static String rule_id = "simple_rule";
    private static String element_code = "age";
    private static String element_code_country = "country";


    /**
     * 规则测试类
     * 简单的规则（年龄>=18 && country=中国）|| （年龄>=20 && country=日本 && sex=男）就是成年人
     *      第1组条件：
     *          条件1:（age>=18）：左值age为元素{@link Element}，即入参, 右值为常量{@link Constant}等于18
     *          条件2:（country>=中国）：左值country为元素{@link Element}，即入参, 右值为常量{@link Constant}等于中国
     *      第2组条件：
     *          条件1:（age>=20）：左值age为元素{@link Element}，即入参, 右值为常量{@link Constant}等于20
     *          条件2:（country>=日本）：左值country为元素{@link Element}，即入参, 右值为常量{@link Constant}等于日本
     *          条件3:（sex>=男）：左值sex为元素{@link Element}，即入参, 右值为常量{@link Constant}等于男
     * 满足任意一组条件，结果为true
     */
    @Test
    public void isAdult1() throws Exception {
        DefaultRuleEngine ruleEngine = new DefaultRuleEngine();
        final AbstractRule rule = getRule();
        ruleEngine.addRule(rule);
        //获取当前规则的入参
        final Collection<Element> elements = rule.collectParameter();
        System.out.println(elements);
        //中国人18就是成年人
        final BaseContextImpl context = new BaseContextImpl();
        context.put(element_code, 20);
        context.put(element_code_country, "中国");
         Object result = ruleEngine.execute(rule_id, context);
        Assert.assertTrue((Boolean) result);
        //日本人20岁不一定是成年人
        final BaseContextImpl contextJp = new BaseContextImpl();
        contextJp.put(element_code, 20);
        contextJp.put(element_code_country, "日本1");
         result = ruleEngine.execute(rule_id, contextJp);
        Assert.assertEquals(result, RuleResultEnum.NULL);

        //日本男人20岁才是成年人
        final BaseContextImpl contextJpMan = new BaseContextImpl();
        contextJpMan.put(element_code, 20);
        contextJpMan.put(element_code_country, "日本");
        contextJpMan.put("sex", "男");
        result = ruleEngine.execute(rule_id, context);
        Assert.assertTrue((Boolean) result);


    }


    public AbstractRule getRule() {
        final DefaultCondition adult = new DefaultCondition(new Element(DataTypeEnum.NUMBER, element_code), Symbol.number_ge, new Constant(DataTypeEnum.NUMBER, 18));
        final DefaultCondition adult1 = new DefaultCondition(new Element(DataTypeEnum.STRING, element_code_country), Symbol.str_eq, new Constant(DataTypeEnum.STRING, "中国"));
        ConditionGroup conditionGroup1 = new ConditionGroup(Arrays.asList(adult, adult1));

        final DefaultCondition ageCondition = new DefaultCondition(new Element(DataTypeEnum.NUMBER, element_code), Symbol.number_ge, new Constant(DataTypeEnum.NUMBER, 20));
        final DefaultCondition countryCondition = new DefaultCondition(new Element(DataTypeEnum.STRING, element_code_country), Symbol.str_eq, new Constant(DataTypeEnum.STRING, "日本"));
        final DefaultCondition sexCondition = new DefaultCondition(new Element(DataTypeEnum.STRING, "sex"), Symbol.str_eq, new Constant(DataTypeEnum.STRING, "男"));
        ConditionGroup conditionGroup2 = new ConditionGroup(Arrays.asList(ageCondition, countryCondition, sexCondition));

        AbstractCondition conditionSet = new ConditionSet(Arrays.asList(conditionGroup1, conditionGroup2));

        return new Rule(rule_id, conditionSet, new Constant(DataTypeEnum.BOOLEAN, true));
    }





}
