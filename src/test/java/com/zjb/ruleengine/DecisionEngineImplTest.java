package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.DefaultRuleEngine;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.decistiontree.DecisionColInfo;
import com.zjb.ruleengine.core.decistiontree.DecisionCondition;
import com.zjb.ruleengine.core.decistiontree.DecisionRow;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.rule.DecisionRuleSet;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.zjb.ruleengine.core.enums.Symbol.eq;

public class DecisionEngineImplTest {
    @Test
    public void createDecisionEngine(){
        DefaultRuleEngine decisionEngine = new DefaultRuleEngine();
        DecisionRuleSet decisionRuleSet = null;
        //decisionRuleSet.setCode("test");
        List<DecisionRow> decisionRows = new ArrayList<>();
        /* 插入第一行 */
        DecisionRow decisionRow = new DecisionRow();
        decisionRow.setPriority(1);
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(5);
        arrayList.add(10);
        arrayList.add(20);
        DecisionColInfo decisionColInfo = new DecisionColInfo();
        Value leftValue = new Element(DataTypeEnum.NUMBER ,"contract");
        Constant constant = new Constant(DataTypeEnum.NUMBER, "contract");
        DecisionCondition decisionCondition = new DecisionCondition("",leftValue, eq, constant);
        List<DecisionColInfo> decisionColInfos = new ArrayList<>();

        decisionColInfo.setDecisionCondition(decisionCondition);
        decisionColInfos.add(decisionColInfo);
        decisionRow.setDecisionColInfos(decisionColInfos);
        Constant result = new Constant( DataTypeEnum.STRING, "contract");
        decisionRow.setResult(result);
        decisionRows.add(decisionRow);
        /* 插入第二行 */
        DecisionRow decisionRow2 = new DecisionRow();
        decisionRow2.setPriority(2);
        DecisionColInfo decisionColInfo2 = new DecisionColInfo();
        Value leftValue2 = new Element( DataTypeEnum.NUMBER ,"contract");
        Constant constant2 = new Constant( DataTypeEnum.NUMBER, "contract");
        DecisionCondition decisionCondition2 = new DecisionCondition("",leftValue2, eq, constant2);
        List<DecisionColInfo> decisionColInfos2 = new ArrayList<>();

        decisionColInfo2.setDecisionCondition(decisionCondition2);
        decisionColInfos2.add(decisionColInfo2);
        decisionRow2.setDecisionColInfos(decisionColInfos2);
        Constant result2 = new Constant( DataTypeEnum.STRING, "contract");
        decisionRow2.setResult(result2);
        decisionRows.add(decisionRow2);

        /* 插入第三行 */
        DecisionRow decisionRow3 = new DecisionRow();
        decisionRow3.setPriority(2);
        DecisionColInfo decisionColInfo3 = new DecisionColInfo();
        List<DecisionColInfo> decisionColInfos3= new ArrayList<>();
        decisionColInfo3.setDecisionCondition(null);
        decisionColInfos3.add(decisionColInfo3);
        decisionRow3.setDecisionColInfos(decisionColInfos3);
        Constant result3 = new Constant( DataTypeEnum.STRING, "contract");
        decisionRow3.setResult(result3);
        decisionRows.add(decisionRow3);

        //decisionRuleSet.setDecisionRows(decisionRows);
        //decisionEngine.addRuleSet(decisionRuleSet);
        Context context = new BaseContextImpl();
        context.put("inParam", 0);
        Object execute = decisionEngine.execute("contract", context);
        System.out.println(execute);
    }
}
