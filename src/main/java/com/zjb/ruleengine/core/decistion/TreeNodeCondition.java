package com.zjb.ruleengine.core.decistion;

import com.zjb.ruleengine.core.condition.AbstractCondition;
import com.zjb.ruleengine.core.value.Value;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;


/**
 * @author 赵静波
 * @date 2020-08-24 19:00:38
 */
public class TreeNodeCondition implements Serializable {
    /**
     * is leaf node, if current node have not child and only have result, it is leaf
     */
    private boolean isLeaf = false;
    /**
     * current node
     */
    private AbstractCondition nodeCondition = null;
    /**
     * children node,it need order
     * if there are one equals to it ,it shouldn't put
     */
    private LinkedHashMap<TreeNodeCondition, Object> childrenNodeConditions = new LinkedHashMap<>();
    /**
     * result ,it need order
     * if there are one equals to it ,it shouldn't add
     */
    private LinkedHashSet<Value> results;

    private static final Object PRESENT = new Object();

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }


    public void addResult(Value action) {
        if (results == null) {
            results = new LinkedHashSet<>();
        }
        results.add(action);
    }

    public boolean containChildNodeCondition(TreeNodeCondition condition) {
        return childrenNodeConditions.containsKey(condition);
    }

    public TreeNodeCondition getChildNodeCondition(TreeNodeCondition condition) {
        return childrenNodeConditions.keySet().stream().filter(con -> con.equals(condition)).findFirst().get();
    }

    public void addChildNodeCondition(TreeNodeCondition condition) {
        if (childrenNodeConditions == null) {
            childrenNodeConditions = new LinkedHashMap<>();
        }
        childrenNodeConditions.put(condition, PRESENT);
    }

    public AbstractCondition getNodeCondition() {
        return nodeCondition;
    }

    public void setNodeCondition(AbstractCondition nodeCondition) {
        this.nodeCondition = nodeCondition;
    }

    public LinkedHashMap<TreeNodeCondition, Object> getChildrenNodeConditions() {
        return childrenNodeConditions;
    }

    public void setChildrenNodeConditions(LinkedHashMap<TreeNodeCondition, Object> childrenNodeConditions) {
        this.childrenNodeConditions = childrenNodeConditions;
    }

    public LinkedHashSet<Value> getResults() {
        return results;
    }

    public void setResults(LinkedHashSet<Value> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return nodeCondition.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNodeCondition that = (TreeNodeCondition) o;
        return Objects.equals(nodeCondition, that.nodeCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeCondition);
    }
}
