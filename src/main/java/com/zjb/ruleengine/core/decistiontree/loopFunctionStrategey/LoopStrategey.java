package com.zjb.ruleengine.core.decistiontree.loopFunctionStrategey;


import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.decistiontree.DecisionForest;

import java.util.List;

/**
 * 循环查找策略
 */
public interface LoopStrategey {

    public List executeLoop(String loopColumn, Context context, DecisionForest decisionForest, List loopList);
}
