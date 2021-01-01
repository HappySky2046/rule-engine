package com.zjb.ruleengine;

import com.google.common.collect.Lists;
import com.zjb.ruleengine.core.condition.ConditionGroup;
import com.zjb.ruleengine.core.condition.ConditionSet;
import com.zjb.ruleengine.core.condition.DefaultCondition;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author 赵静波
 * @date 2020-12-08 20:38:05
 */
public class ConditionTest {
    /**
     * 测试下ConditionSet的原型模式
     */
    @Test
    public void testConditionGroup() {
        final DefaultCondition condtion1 = new DefaultCondition("conditionGroup1_condtion1", getInput1(), Symbol.eq, getResult("1"));
        final DefaultCondition condtion2 = new DefaultCondition("conditionGroup1_condtion2", getInput2(), Symbol.eq, getResult("2"));
        final DefaultCondition condtion3 = new DefaultCondition("conditionGroup1_condtion3", getInput3(), Symbol.eq, getResult("3"));
        ConditionGroup conditionGroup1=new ConditionGroup("",Lists.newArrayList(condtion1, condtion2, condtion3));
        ConditionGroup conditionGroup2=new ConditionGroup("",Lists.newArrayList(condtion1, condtion2, condtion3));
        ConditionGroup conditionGroup3=new ConditionGroup("",Lists.newArrayList(condtion1, condtion2, condtion3));

        final ConditionSet conditionSet = new ConditionSet("", Arrays.asList(conditionGroup1, conditionGroup2, conditionGroup3));

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


}
