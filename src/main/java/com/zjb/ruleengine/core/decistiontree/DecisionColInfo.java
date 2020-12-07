package com.zjb.ruleengine.core.decistiontree;

import java.io.Serializable;

public class DecisionColInfo implements Serializable {


    /**
     * 决策条件
     */
    private DecisionCondition decisionCondition;

    public DecisionCondition getDecisionCondition() {
        return decisionCondition;
    }

    public void setDecisionCondition(DecisionCondition decisionCondition) {
        this.decisionCondition = decisionCondition;
    }
}
