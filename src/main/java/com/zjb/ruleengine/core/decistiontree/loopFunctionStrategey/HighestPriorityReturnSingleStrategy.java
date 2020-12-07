package com.zjb.ruleengine.core.decistiontree.loopFunctionStrategey;

import cn.hutool.core.collection.CollUtil;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.decistiontree.DecisionEngineTreeNode;
import com.zjb.ruleengine.core.decistiontree.DecisionForest;

import java.util.ArrayList;
import java.util.List;

import static com.zjb.ruleengine.core.rule.DecisionRuleSet.deepFind;

public class HighestPriorityReturnSingleStrategy implements LoopStrategey{

    @Override
    public List executeLoop(String loopColumn, Context context, DecisionForest decisionForest, List loopList) {

        List<DecisionEngineTreeNode> decisionEngineTreeNodes = decisionForest.getDecisionEngineTreeNodes();
        List<Object> result = new ArrayList<>();
        for (DecisionEngineTreeNode decisionEngineTreeNode : decisionEngineTreeNodes) {
            for (Object value : loopList) {

                //更换context中的对应参数
                context.put(loopColumn, value);
                deepFind(decisionEngineTreeNode, context, 0, result);
                if(CollUtil.isNotEmpty(result)){
                    return result;
                }
            }
        }
        return result;
    }
}

