package com.zjb.ruleengine.core.decistiontree.loopFunctionStrategey;

import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.decistiontree.DecisionForest;

import java.util.List;

public class StrategyContext {

    private LoopStrategey loopStrategey;

    public StrategyContext(LoopStrategey loopStrategey){
        this.loopStrategey = loopStrategey;
    }

    public List executeLoop(String loopColumn, Context context, DecisionForest decisionForest, List loopList){
        return loopStrategey.executeLoop(loopColumn, context, decisionForest, loopList);
    }




}
