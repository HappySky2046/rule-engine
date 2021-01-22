package com.zjb.ruleengine.ruleset;

import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.DefaultRuleEngine;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.rule.ActionRule;
import com.zjb.ruleengine.core.rule.Rule;
import com.zjb.ruleengine.core.rule.RuleSet;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Collection;

/**
 * @author 赵静波
 * Created on 2021-01-13
 */
public class RuleSetTest {
    private Element element = new Element(DataTypeEnum.NUMBER, "time");
    private String rule_id = "food";
    private String breakfast = "牛奶";
    private String lunch = "大米";
    private String dinner = "粥";
    private String unFood = "不是饭点";

    @Test
    public void ruleSetTest() {
        DefaultRuleEngine ruleEngine = new DefaultRuleEngine();
        //早餐吃什么
        final AbstractRule breakfast = breakfast();
        //午餐吃什么
        final AbstractRule lunch = lunch();
        //晚餐吃什么
        final AbstractRule dinner = dinner();
        //不在饭点吃什么
        final ActionRule unFood = new ActionRule("unFood", new Constant(DataTypeEnum.STRING, this.unFood));
        //加载规则
        RuleSet ruleSet = new RuleSet(rule_id, Lists.newArrayList(breakfast, lunch, dinner, unFood));
        ruleEngine.addRule(ruleSet);

        //需要传参数
        final Collection<Element> elements = ruleSet.collectParameter();
        System.out.println(elements);
        //吃早餐
        final BaseContextImpl baseContext = new BaseContextImpl();
        baseContext.put("time", LocalTime.of(8, 30).toSecondOfDay());
        Object execute = ruleEngine.execute(rule_id, baseContext);
        System.out.println(execute);
        Assert.assertEquals(execute, this.breakfast);
        //吃午餐
        baseContext.put("time", LocalTime.of(12, 30).toSecondOfDay());
        execute = ruleEngine.execute(rule_id, baseContext);
        System.out.println(execute);
        Assert.assertEquals(execute, this.lunch);
        //吃晚餐
        baseContext.put("time", LocalTime.of(18, 30).toSecondOfDay());
        execute = ruleEngine.execute(rule_id, baseContext);
        System.out.println(execute);
        Assert.assertEquals(execute, this.dinner);
        //其它时间吃饭
        baseContext.put("time", LocalTime.of(20, 30).toSecondOfDay());
        execute = ruleEngine.execute(rule_id, baseContext);
        System.out.println(execute);
        Assert.assertEquals(execute, this.unFood);

    }

    /**
     * '
     * 早餐
     *
     * @return
     */
    private AbstractRule breakfast() {
        final int startTime = LocalTime.of(8, 0).toSecondOfDay();
        final int endTime = LocalTime.of(9, 0).toSecondOfDay();

        final DefaultCondition startTimeCon = new DefaultCondition(element, Symbol.number_ge, new Constant(DataTypeEnum.NUMBER, startTime));
        final DefaultCondition endTimeCon = new DefaultCondition(new Constant(DataTypeEnum.NUMBER, endTime), Symbol.number_ge, element);
        final ConditionGroup conditionGroup = new ConditionGroup(Lists.newArrayList(startTimeCon, endTimeCon));

        return new Rule("breakfast", conditionGroup, new Constant(DataTypeEnum.STRING, breakfast));
    }

    /**
     * '
     * 午餐
     *
     * @return
     */
    private AbstractRule lunch() {
        final int startTime = LocalTime.of(12, 0).toSecondOfDay();
        final int endTime = LocalTime.of(13, 0).toSecondOfDay();

        final DefaultCondition startTimeCon = new DefaultCondition(element, Symbol.number_ge, new Constant(DataTypeEnum.NUMBER, startTime));
        final DefaultCondition endTimeCon = new DefaultCondition(new Constant(DataTypeEnum.NUMBER, endTime), Symbol.number_ge, element);
        final ConditionGroup conditionGroup = new ConditionGroup(Lists.newArrayList(startTimeCon, endTimeCon));

        return new Rule("lunch", conditionGroup, new Constant(DataTypeEnum.STRING, lunch));
    }

    /**
     * '
     * 午餐
     *
     * @return
     */
    private AbstractRule dinner() {
        final int startTime = LocalTime.of(18, 0).toSecondOfDay();
        final int endTime = LocalTime.of(19, 0).toSecondOfDay();

        final DefaultCondition startTimeCon = new DefaultCondition(element, Symbol.number_ge, new Constant(DataTypeEnum.NUMBER, startTime));
        final DefaultCondition endTimeCon = new DefaultCondition(new Constant(DataTypeEnum.NUMBER, endTime), Symbol.number_ge, element);
        final ConditionGroup conditionGroup = new ConditionGroup(Lists.newArrayList(startTimeCon, endTimeCon));

        return new Rule("dinner", conditionGroup, new Constant(DataTypeEnum.STRING, dinner));
    }


}
