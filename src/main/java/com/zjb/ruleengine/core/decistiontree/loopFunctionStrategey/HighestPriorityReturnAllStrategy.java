package com.zjb.ruleengine.core.decistiontree.loopFunctionStrategey;

import cn.hutool.core.collection.CollUtil;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.decistiontree.DecisionEngineTreeNode;
import com.zjb.ruleengine.core.decistiontree.DecisionForest;

import java.util.ArrayList;
import java.util.List;

import static com.zjb.ruleengine.core.rule.DecisionRuleSet.deepFind;

public class HighestPriorityReturnAllStrategy implements LoopStrategey{

    @Override
    public List executeLoop(String loopColumn, Context context, DecisionForest decisionForest, List loopList) {
        List<Object> answer = new ArrayList<>();
        List<DecisionEngineTreeNode> decisionEngineTreeNodes = decisionForest.getDecisionEngineTreeNodes();
        for (DecisionEngineTreeNode decisionEngineTreeNode : decisionEngineTreeNodes) {

            for (Object value : loopList) {
                List<Object> result = new ArrayList<>();
                //更换context中的对应参数
                context.put(loopColumn, value);
                deepFind(decisionEngineTreeNode, context, 0, result);
                answer.addAll(result);
            }
            if(CollUtil.isNotEmpty(answer)){
                return answer;
            }
        }

        return answer;
    }
}
