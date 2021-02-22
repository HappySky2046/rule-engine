package com.zjb.ruleengine.core.decistiontree;

import com.zjb.ruleengine.core.value.Value;

import java.io.Serializable;
import java.util.List;

public class DecisionRow implements Serializable {
    private List<DecisionColInfo> decisionColInfos;
    private Value result;
    private Integer priority;

    public List<DecisionColInfo> getDecisionColInfos() {
        return decisionColInfos;
    }

    public void setDecisionColInfos(List<DecisionColInfo> decisionColInfos) {
        this.decisionColInfos = decisionColInfos;
    }

    public Value getResult() {
        return result;
    }

    public void setResult(Value result) {
        this.result = result;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
