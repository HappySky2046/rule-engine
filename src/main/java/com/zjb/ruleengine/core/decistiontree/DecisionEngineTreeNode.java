package com.zjb.ruleengine.core.decistiontree;

import com.zjb.ruleengine.core.value.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhiji
 */
public class DecisionEngineTreeNode implements Serializable{

    private boolean isLeaf = false;
    private DecisionCondition decisionCondition = null;
    private List<DecisionEngineTreeNode> decisionEngineTreeNodeList = new ArrayList<>();
    private List<Value> results = new ArrayList<>();

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public DecisionCondition getDecisionCondition() {
        return decisionCondition;
    }

    public void setDecisionCondition(DecisionCondition decisionCondition) {
        this.decisionCondition = decisionCondition;
    }

    public List<DecisionEngineTreeNode> getDecisionEngineTreeNodeList() {
        return decisionEngineTreeNodeList;
    }

    public void setDecisionEngineTreeNodeList(List<DecisionEngineTreeNode> decisionEngineTreeNodeList) {
        this.decisionEngineTreeNodeList = decisionEngineTreeNodeList;
    }

    public List<Value> getResults() {
        return results;
    }

    public void setResults(List<Value> results) {
        this.results = results;
    }
}
