package com.zjb.ruleengine.core.decistiontree;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuzhiji
 */
public class DecisionForest implements Serializable {

    private List<DecisionEngineTreeNode> decisionEngineTreeNodes;

    public List<DecisionEngineTreeNode> getDecisionEngineTreeNodes() {
        return decisionEngineTreeNodes;
    }

    public void setDecisionEngineTreeNodes(List<DecisionEngineTreeNode> decisionEngineTreeNodes) {
        this.decisionEngineTreeNodes = decisionEngineTreeNodes;
    }
}
