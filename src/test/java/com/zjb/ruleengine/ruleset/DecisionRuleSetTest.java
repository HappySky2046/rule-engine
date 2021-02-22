package com.zjb.ruleengine.ruleset;

import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.rule.AbstractRule;
import com.zjb.ruleengine.core.rule.DecisionRuleSet;
import com.zjb.ruleengine.core.rule.Rule;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author 赵静波
 * @date 2020-12-08 14:03:02
 */
public class DecisionRuleSetTest {


    @Test
    public void testBuild() {
        final ArrayList<AbstractRule> rules = getRules();

        DecisionRuleSet ruleSet = new DecisionRuleSet("",rules);
        ruleSet.build();
        System.out.println();
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

    private ArrayList<AbstractRule> getRules() {
        final ArrayList<AbstractCondition> conditionGroup1 = Lists.newArrayList(
                new DefaultCondition("conditionGroup1_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup1_condtion2", getInput2(), Symbol.number_eq, getResult("2")),
                new DefaultCondition("conditionGroup1_condtion3", getInput3(), Symbol.number_eq, getResult("3"))
        );
        final Rule rule1 = new Rule("rule1", new ConditionGroup("conditionGroup1", conditionGroup1), getResult("result1"));

        final ArrayList<AbstractCondition> conditionGroup2 = Lists.newArrayList(
                new DefaultCondition("conditionGroup2_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup2_condtion2", getInput2(), Symbol.number_eq, getResult("2")),
                new DefaultCondition("conditionGroup2_condtion3", getInput3(), Symbol.number_eq, getResult("3"))
        );
        final Rule rule2 = new Rule("rule2", new ConditionGroup("conditionGroup2", conditionGroup2), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup3 = Lists.newArrayList(
                new DefaultCondition("conditionGroup3_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup3_condtion2", getInput2(), Symbol.number_eq, getResult("2")),
                new DefaultCondition("conditionGroup3_condtion3", getInput3(), Symbol.number_eq, getResult("33"))
        );
        final Rule rule3 = new Rule("rule3", new ConditionGroup("conditionGroup3", conditionGroup3), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup4 = Lists.newArrayList(
                new DefaultCondition("conditionGroup4_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup4_condtion2", getInput2(), Symbol.number_eq, getResult("2")),
                new DefaultCondition("conditionGroup4_condtion3", getInput3(), Symbol.number_eq, getResult("33"))
        );
        final Rule rule4 = new Rule("rule4", new ConditionGroup("conditionGroup4", conditionGroup4), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup5 = Lists.newArrayList(
                new DefaultCondition("conditionGroup5_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup5_condtion2", getInput2(), Symbol.number_eq, getResult("22")),
                new DefaultCondition("conditionGroup5_condtion3", getInput3(), Symbol.number_eq, getResult("33"))
        );
        final Rule rule5 = new Rule("rule5", new ConditionGroup("conditionGroup5", conditionGroup5), getResult("result2"));

        final ArrayList<AbstractCondition> conditionGroup6 = Lists.newArrayList(
                new DefaultCondition("conditionGroup6_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup6_condtion2", getInput2(), Symbol.number_eq, getResult("22")),
                new DefaultCondition("conditionGroup6_condtion3", getInput3(), Symbol.number_eq, getResult("33"))
        );
        final Rule rule6 = new Rule("rule6", new ConditionGroup("conditionGroup6", conditionGroup6), getResult("result3"));

        final ArrayList<AbstractCondition> conditionGroup7 = Lists.newArrayList(
                new DefaultCondition("conditionGroup7_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup7_condtion2", getInput2(), Symbol.number_eq, getResult("22")),
                new DefaultCondition("conditionGroup7_condtion3", getInput3(), Symbol.number_eq, getResult("333"))
        );
        final Rule rule7 = new Rule("rule7", new ConditionGroup("conditionGroup7", conditionGroup7), getResult("result3"));

        final ArrayList<AbstractCondition> conditionGroup8 = Lists.newArrayList(
                new DefaultCondition("conditionGroup8_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup8_condtion2", getInput2(), Symbol.number_eq, getResult("222")),
                new DefaultCondition("conditionGroup8_condtion3", getInput3(), Symbol.number_eq, getResult("333"))
        );
        final Rule rule8 = new Rule("rule8", new ConditionGroup("conditionGroup8", conditionGroup8), getResult("result5"));

        final ArrayList<AbstractCondition> conditionGroup9 = Lists.newArrayList(
                new DefaultCondition("conditionGroup9_condtion1", getInput1(), Symbol.number_eq, getResult("1")),
                new DefaultCondition("conditionGroup9_condtion2", getInput2(), Symbol.number_eq, getResult("222")),
                new DefaultCondition("conditionGroup9_condtion3", getInput3(), Symbol.number_eq, getResult("333"))
        );
        final Rule rule9 = new Rule("rule9", new ConditionGroup("conditionGroup9", conditionGroup9), getResult("result6"));


        final ArrayList<AbstractRule> rules = Lists.newArrayList();
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
