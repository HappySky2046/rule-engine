
package com.zjb.ruleengine;

import cn.hutool.core.collection.CollectionUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 规则引擎测试类
 *  *
 **/
public class ApprovalRuleEngineTest {
    @Test
    @Ignore
    public void execute() throws Exception {
        /**
         * 构造如下规则：一次命中策略
         * 规则1：如果合同金额<=100000，合同类型=151，则审批人为a,b,c
         * 规则2：如果合同金额<=100000，合同类型=152，则审批人为d,e
         * 规则3：如果合同金额>100000，则审批人为f
         **/
        //RuleEngine ruleEngine = new BaseRuleEngine();
        //RuleSet ruleSet = new RuleSet();
        //ruleSet.setRuleCode("ct_ic_vp2");
        //
        //Rule rule1 = new Rule();1
        //Rule rule2 = new Rule();
        //Rule rule3 = new Rule();
        //
        //ConditionSet conditionSet1 = new ConditionSet();
        //ConditionSet conditionSet2 = new ConditionSet();
        //ConditionSet conditionSet3 = new ConditionSet();
        //
        //ActionExecute approvalAction1 = new ApprovalAction();
        //ActionExecute approvalAction2 = new ApprovalAction();
        //
        //
        ////构造第一个condition，左值变量名contract_number，运算符=，右值151
        //Condition condition1 = new NumberExpCondition("contract_number", Symbol.eq, 151);
        //
        ////构造第二个condition，左值变量名contract_amount，运算符<=，右值10000
        //Condition condition2 = new NumberExpCondition("contract_amount", Symbol.le, 10000);
        //
        ////构造第三个condition，左值变量名contract_number，运算符=，右值152
        //Condition condition3 = new NumberExpCondition("contract_number", Symbol.ne, 152);
        //
        ////构造第四个condition，左值变量名contract_amount，运算符>，右值10000
        //Condition condition4 = new NumberExpCondition("contract_amount", Symbol.lt, 10000);
        //
        //// Rule #1
        //List<Condition> conditionsAndList1 = new ArrayList<>();
        //conditionsAndList1.add(condition1);
        //conditionsAndList1.add(condition2);
        //conditionSet1.addNewConditionList(conditionsAndList1);
        //approvalAction1.setActionVariableName("approver");
        //approvalAction1.setResult("a,b,c");
        //rule1.setConditionSet(conditionSet1);
        //rule1.setActionExecute(approvalAction1);
        //
        //// Rule #2
        //List<Condition> conditionsAndList2 = new ArrayList<>();
        //conditionsAndList2.add(condition2);
        //conditionsAndList2.add(condition3);
        //conditionSet2.addNewConditionList(conditionsAndList2);
        //approvalAction2.setActionVariableName("approver");
        //approvalAction2.setResult("d,e");
        //rule2.setConditionSet(conditionSet2);
        //rule2.setActionExecute(approvalAction2);
        //
        //// Rule #3
        //List<Condition> conditionsAndList3 = new ArrayList<>();
        //conditionsAndList3.add(condition4);
        //conditionSet3.addNewConditionList(conditionsAndList3);
        //
        //ActionExecute approvalAction3 = new RefAction();
        //approvalAction3.setActionVariableName("approver");
        //approvalAction3.setResult("f");
        //rule3.setConditionSet(conditionSet3);
        //rule3.setActionExecute(approvalAction3);
        //
        ////加入规则
        //ruleSet.addRule(rule1);
        //ruleSet.addRule(rule2);
        //ruleSet.addRule(rule3);
        //ruleSet.setHitPolicy(0);
        //
        ////加入引擎
        //ruleEngine.addRuleSet(ruleSet);
        //
        ////构建两个不同的context
        //Context context1 = new BaseContextImpl();
        //context1.put("contract_number", 151);
        //context1.put("contract_amount", 1000);
        ////构建两个不同的context
        //Context context2 = new BaseContextImpl();
        //context2.put("contract_number", 152);
        //context2.put("contract_amount", 1000);
        //
        ////构建两个不同的context
        //Context context3 = new BaseContextImpl();
        //context3.put("contract_number", 153);
        //context3.put("contract_amount", 50000);
        //
        //ruleEngine.execute(context1, "ct_ic_vp2");
        //assertEquals("a", ((List<String>)context1.get("approver")).get(0));
        //
        //ruleEngine.execute(context2, "ct_ic_vp2");
        //assertEquals("d", ((List<String>)context2.get("approver")).get(0));
        //
        //ruleEngine.execute(context3, "ct_ic_vp2");
        //assertEquals("f", ((List<String>)context3.get("approver")).get(0));

    }


    @Test
    @Ignore
    public void testRepitition() throws Exception {
        /**
         * 构造如下规则：一次命中策略
         * 规则1：如果合同金额<=100000，合同类型=151，则审批人为a,b,c
         * 规则2：如果合同金额<=100000，合同类型=152，则审批人为d,e
         * 规则3：如果合同金额>100000，则审批人为f
         **/
        int nodeRepetitionPolicy = 1;
        LinkedHashSet repetitionPolicyResult = new LinkedHashSet();
        List results = Arrays.asList(1,2,3,4,5);
        List list = (List)results.stream().map(e->{
            String tempString = (String)e;
            String[] tempList = tempString.split("/");
            return tempList[0];
        }).collect(Collectors.toList());
        switch (nodeRepetitionPolicy) {
            case 1:
                repetitionPolicyResult.addAll(CollectionUtil.reverse(list));
                results = CollectionUtil.reverse(new ArrayList(repetitionPolicyResult));
                break;
            case 2:
            case 3:
                repetitionPolicyResult.addAll(list);
                results = new ArrayList(repetitionPolicyResult);
                break;
            default:
                break;

        }
        for(Object object: results){
            System.out.println(object);
        }

    }
}
