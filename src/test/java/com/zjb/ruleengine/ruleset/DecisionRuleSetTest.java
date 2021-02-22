package com.zjb.ruleengine.ruleset;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.RuleSetExecutePolicyEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.rule.DecisionRuleSet;
import com.zjb.ruleengine.core.rule.Rule;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author 赵静波
 * @date 2020-12-08 14:03:02
 */
public class DecisionRuleSetTest {

    /**
     * input1	input2	input3	result
     * 1		2		3		result1						 	 	 1
     * 1		2		3		result2					 /  	 	 |   		\
     * 1		2		33		result2					2		 	 22			222
     * 1		2		33		result2 =======>	   / \  	   /  	\   	 |
     * 1		22		33		result2				  3   33   	  33  	333   	333
     * 1		22		33		result3              / \   |   	 / \   	 |    	/ \
     * 1		22		333		result3             r1 r2  r2   r2  r3   r3    r5  r6
     * 1		222		333		result5
     * 1		222		333		result6
     */
    @Test
    public void testBuild() {
        final ArrayList<Rule> rules = getRules();

        DecisionRuleSet ruleSet = new DecisionRuleSet("123", rules);
        ruleSet.setPolicy(RuleSetExecutePolicyEnum.ALL);
        ruleSet.build();
        final BaseContextImpl context = new BaseContextImpl();
        context.put("input1", 111);
        context.put("input2", 2222);
        context.put("input3", 33333);
        Object execute = ruleSet.execute(context);
        //回溯
        Assert.assertTrue(Lists.newArrayList("result1, result2, result2, result2, result3, result3, result5, result6".split(",")).retainAll((Collection)execute));
        ruleSet.setPolicy(RuleSetExecutePolicyEnum.ONE);
        context.put("input1", 111);
        context.put("input2", 2222);
        context.put("input3", 33333);
        execute = ruleSet.execute(context);
        //递归
        Assert.assertEquals(execute,"result1");

    }

    private Element getInput1() {
        return new Element(DataTypeEnum.NUMBER, "input1");
    }

    private Element getInput2() {
        return new Element(DataTypeEnum.NUMBER, "input2");
    }

    private Element getInput3() {
        return new Element(DataTypeEnum.NUMBER, "input3");
    }

    private Value getResult(String result) {
        return new Constant(DataTypeEnum.STRING, result);
    }

    private Value getRightInput(String value) {
        return new Constant(DataTypeEnum.NUMBER, Integer.parseInt(value));
    }

    private ArrayList<Rule> getRules() {
        final ArrayList<AbstractCondition> conditionGroup1 = Lists.newArrayList(
                new DefaultCondition("conditionGroup1_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup1_condtion2", getInput2(), Symbol.number_ge, getRightInput("2")),
                new DefaultCondition("conditionGroup1_condtion3", getInput3(), Symbol.number_ge, getRightInput("3"))
        );
        final Rule rule1 = new Rule("rule1", new ConditionGroup("conditionGroup1", conditionGroup1), getResult("result1"));

        final ArrayList<AbstractCondition> conditionGroup2 = Lists.newArrayList(
                new DefaultCondition("conditionGroup2_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup2_condtion2", getInput2(), Symbol.number_ge, getRightInput("2")),
                new DefaultCondition("conditionGroup2_condtion3", getInput3(), Symbol.number_ge, getRightInput("3"))
        );
        final Rule rule2 = new Rule("rule2", new ConditionGroup("conditionGroup2", conditionGroup2), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup3 = Lists.newArrayList(
                new DefaultCondition("conditionGroup3_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup3_condtion2", getInput2(), Symbol.number_ge, getRightInput("2")),
                new DefaultCondition("conditionGroup3_condtion33", getInput3(), Symbol.number_ge, getRightInput("33"))
        );
        final Rule rule3 = new Rule("rule3", new ConditionGroup("conditionGroup3", conditionGroup3), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup4 = Lists.newArrayList(
                new DefaultCondition("conditionGroup4_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup4_condtion2", getInput2(), Symbol.number_ge, getRightInput("2")),
                new DefaultCondition("conditionGroup4_condtion33", getInput3(), Symbol.number_ge, getRightInput("33"))
        );
        final Rule rule4 = new Rule("rule4", new ConditionGroup("conditionGroup4", conditionGroup4), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup5 = Lists.newArrayList(
                new DefaultCondition("conditionGroup5_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup5_condtion22", getInput2(), Symbol.number_ge, getRightInput("22")),
                new DefaultCondition("conditionGroup5_condtion33", getInput3(), Symbol.number_ge, getRightInput("33"))
        );
        final Rule rule5 = new Rule("rule5", new ConditionGroup("conditionGroup5", conditionGroup5), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup6 = Lists.newArrayList(
                new DefaultCondition("conditionGroup6_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup6_condtion22", getInput2(), Symbol.number_ge, getRightInput("22")),
                new DefaultCondition("conditionGroup6_condtion33", getInput3(), Symbol.number_ge, getRightInput("33"))
        );
        final Rule rule6 = new Rule("rule6", new ConditionGroup("conditionGroup6", conditionGroup6), getResult("result3"));

        final ArrayList<AbstractCondition> conditionGroup7 = Lists.newArrayList(
                new DefaultCondition("conditionGroup7_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup7_condtion22", getInput2(), Symbol.number_ge, getRightInput("22")),
                new DefaultCondition("conditionGroup7_condtion333", getInput3(), Symbol.number_ge, getRightInput("333"))
        );
        final Rule rule7 = new Rule("rule7", new ConditionGroup("conditionGroup7", conditionGroup7), getResult("result3"));

        final ArrayList<AbstractCondition> conditionGroup8 = Lists.newArrayList(
                new DefaultCondition("conditionGroup8_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup8_condtion222", getInput2(), Symbol.number_ge, getRightInput("222")),
                new DefaultCondition("conditionGroup8_condtion333", getInput3(), Symbol.number_ge, getRightInput("333"))
        );
        final Rule rule8 = new Rule("rule8", new ConditionGroup("conditionGroup8", conditionGroup8), getResult("result5"));

        final ArrayList<AbstractCondition> conditionGroup9 = Lists.newArrayList(
                new DefaultCondition("conditionGroup9_condtion1", getInput1(), Symbol.number_ge, getRightInput("1")),
                new DefaultCondition("conditionGroup9_condtion222", getInput2(), Symbol.number_ge, getRightInput("222")),
                new DefaultCondition("conditionGroup9_condtion333", getInput3(), Symbol.number_ge, getRightInput("333"))
        );
        final Rule rule9 = new Rule("rule9", new ConditionGroup("conditionGroup9", conditionGroup9), getResult("result6"));


        final ArrayList<Rule> rules = Lists.newArrayList();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        rules.add(rule4);
        rules.add(rule5);
        rules.add(rule6);
        rules.add(rule7);
        rules.add(rule8);
        rules.add(rule9);
        return rules;

    }
}
