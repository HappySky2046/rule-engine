
package com.zjb.ruleengine.core.condition;

import cn.hutool.core.text.StrFormatter;
import com.google.common.collect.Sets;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.condition.evaluate.*;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.value.Element;
import com.zjb.ruleengine.core.value.Value;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 * @author 赵静波
 * @date 2020-09-30 14:06:41
 */
public class DefaultCondition extends AbstractCondition {
    private static final Logger log = LogManager.getLogger();
    /**
     * 条件左值
     */
    private Value leftValue;
    private Symbol symbol;
    private Value rightValue;

    private static Map<DataTypeEnum, Evaluate> evaluateMap = new HashMap<>();

    static {
        evaluateMap.put(DataTypeEnum.BOOLEAN, BooleanEvaluateStrategy.getInstance());
        evaluateMap.put(DataTypeEnum.COLLECTION, CollectionEvaluateStrategy.getInstance());
        evaluateMap.put(DataTypeEnum.NUMBER, NumberEvaluateStrategy.getInstance());
        evaluateMap.put(DataTypeEnum.STRING, StringEvaluateStrategy.getInstance());
    }

    public DefaultCondition(String id, Value leftValue, Symbol symbol, Value rightValue) {
        super(id);
        Validate.notNull(leftValue);
        Validate.notNull(rightValue);
        Validate.notNull(symbol);
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.symbol = symbol;
    }

    public DefaultCondition(Value leftValue, Symbol symbol, Value rightValue) {
        this("",leftValue, symbol, rightValue);
    }

    @Override
    public Collection<Element> collectParameter() {
        final HashSet<Element> result = Sets.newHashSet();
        result.addAll(leftValue.collectParameter());
        result.addAll(rightValue.collectParameter());
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftValue,symbol,rightValue);
    }

    @Override
    public boolean evaluate(Context context) {
        Object leftValue = this.leftValue.getValue(context);
        Object rightValue = this.rightValue.getValue(context);
        final boolean result = evaluateMap.get(symbol.getType()).evaluate(leftValue, rightValue, symbol);
        log.debug("条件{}--【 {} {} {} 】执行，执行结果{}",getId(),leftValue,symbol.getSymbol(),rightValue,result);
        return result;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Value getLeftValue() {
        return leftValue;
    }

    public Value getRightValue() {
        return rightValue;
    }

    public void setLeftValue(Value leftValue) {
        this.leftValue = leftValue;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public void setRightValue(Value rightValue) {
        this.rightValue = rightValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DefaultCondition)) {
            return false;
        }
        DefaultCondition other1 = (DefaultCondition) other;
        if (other1.getSymbol() != this.getSymbol()) {
            return false;
        }
        if (!other1.getLeftValue().equals(this.getLeftValue())) {
            return false;
        }
        if (!other1.getRightValue().equals(this.getRightValue())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return StrFormatter.format("{}[{}{}{}]",this.getId(),leftValue.getId(),symbol.getSymbol(),rightValue.getId());
    }

    @Override
    public int getWeight() {
        return leftValue.getWeight() + rightValue.getWeight();
    }
}