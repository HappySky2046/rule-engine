
package com.zjb.ruleengine.core.condition.evaluate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import com.zjb.ruleengine.core.enums.Symbol;
import com.zjb.ruleengine.core.exception.RuleEngineException;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.zjb.ruleengine.core.condition.evaluate.BooleanEvaluateStrategy.FALSE_STRING;
import static com.zjb.ruleengine.core.condition.evaluate.BooleanEvaluateStrategy.TRUE_STRING;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:00
 */
//@Slf4j
public class CollectionEvaluateStrategy implements Evaluate {

    private static Evaluate strategy = new CollectionEvaluateStrategy();

    public static Evaluate getInstance() {
        return strategy;
    }

    @Override
    public boolean evaluate(Object leftValue, Object rightValue, Symbol operatorType) {

        if (leftValue == null || rightValue == null) {
            return false;
        }
        if (!(leftValue instanceof Collection)) {
            throw new RuleEngineException("左值必须是Collection");
        }
        Collection leftValueColl = (Collection) leftValue;
        switch (operatorType) {
            case list_in:
                if (!(rightValue instanceof Collection)) {
                    throw new RuleEngineException("collection中in操作右值必须是collection");
                }
                return collectionContainCollection((Collection) leftValue, (Collection) rightValue);
            case list_not_in:
                if (!(rightValue instanceof Collection)) {
                    throw new RuleEngineException("collection中notIn操作右值必须是collection");
                }
                return !collectionContainCollection((Collection) leftValue, (Collection) rightValue);
            case set_eq:
                return setEq(rightValue, leftValueColl);
            case list_contain:
                if (rightValue instanceof Collection) {
                    return collectionContainCollection(rightValue, leftValueColl);
                }
                return listContain(rightValue, leftValueColl);
            case list_not_contain:
                if (!(leftValue instanceof Collection)) {
                    throw new RuleEngineException("左值必须是Collection");
                }
                if (rightValue instanceof Collection) {
                    return !collectionContainCollection(rightValue, leftValueColl);
                }
                return !listContain(rightValue, leftValueColl);
            default:
                //log.warn("操作符" + operatorType + "不匹配");
                throw new RuleEngineException("不支持的操作类型");
        }
    }

    /**
     * collection 包含 collection
     * 空集 包含 空集 =false
     * 非空集 包含 空集 =false
     *
     * @param rightValue
     * @param leftValueColl
     * @return
     */
    private boolean collectionContainCollection(Object rightValue, Collection leftValueColl) {
        Collection rightValueColl = (Collection) rightValue;
        if (CollUtil.isEmpty(leftValueColl) || CollUtil.isEmpty(rightValueColl)) {
            return false;
        }
        Set rightSet = (Set)rightValueColl.stream().map(val -> val + "").collect(Collectors.toSet());
        Set leftSet = (Set)leftValueColl.stream().map(val -> val + "").collect(Collectors.toSet());
        int leftSize = leftSet.size();
        leftSet.addAll(rightSet);
        if (leftSet.size() == leftSize) {
            return true;
        }
        return false;
    }

    private boolean listContain(Object rightValue, Collection leftValueColl) {
        List leftValues = getLeftVal(leftValueColl, rightValue);
        return leftValues.contains(rightValue);
    }

    private boolean setEq(Object rightValue, Collection leftValueColl) {
        if (!(rightValue instanceof Collection)) {
            throw new RuleEngineException("collection=collection 右值必须是collection");
        }
        Collection rightValueColl = (Collection) rightValue;

        Set rightValueSet = (Set)rightValueColl.stream().map(val -> val + "").collect(Collectors.toSet());
        Set leftValueSet = (Set)leftValueColl.stream().map(val -> val + "").collect(Collectors.toSet());

        if (leftValueSet.size() != rightValueSet.size()) {
            return false;
        }
        // 如果set.addAll(set) size相等，则两个set必然相等
        leftValueSet.addAll(rightValueSet);
        if (leftValueSet.size() == rightValueSet.size()) {
            return true;
        }
        return false;
    }

    private List getLeftVal(Collection leftValue, Object rightValue) {
        Collection leftValueColl = leftValue;
        // 保证collection中的元素 class和rightValue class相同
        List leftValues = new ArrayList(leftValueColl.size());
        for (Object leftVal : leftValueColl) {
            if (leftVal.getClass() != rightValue.getClass()) {
                if (rightValue instanceof Boolean) {
                    //因为Boolean.valueOf(任意非boolean的字符) 都为false，所以需要判断是否 eq "true" 或 "false"
                    leftVal += "";
                    if (Objects.equals(leftVal, TRUE_STRING)) {
                        leftVal = true;
                    } else if (Objects.equals(leftVal, FALSE_STRING)) {
                        leftVal = false;
                    }
                } else if (rightValue instanceof Number) {
                    if (NumberUtils.isDigits(leftVal + "")) {
                        leftVal = NumberUtils.createNumber(leftVal + "");
                    }
                } else if (rightValue instanceof String) {
                    leftVal += "";
                } else {
                    throw new RuleEngineException(StrFormatter.format("类型转换失败{}", rightValue.getClass().getName()));
                }
            }
            leftValues.add(leftVal);
        }
        return leftValues;
    }

    public static void main(String[] args) {
        System.out.println(strategy.evaluate(Arrays.asList(1, 2), Arrays.asList(1, 2, 3), Symbol.list_in));
        System.out.println(strategy.evaluate(Arrays.asList(1, 2), Arrays.asList(1, 2, 3), Symbol.list_not_in));
        System.out.println(strategy.evaluate(1, Arrays.asList(1, 2, 3), Symbol.list_not_in));
        System.out.println(strategy.evaluate(4, Arrays.asList(1, 2, 3), Symbol.list_not_in));
    }

}