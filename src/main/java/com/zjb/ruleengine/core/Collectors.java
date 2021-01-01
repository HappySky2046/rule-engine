package com.zjb.ruleengine.core;

import com.zjb.ruleengine.core.value.Element;

import java.util.Collection;

/**
 * @author 赵静波
 * @date 2020-12-07 10:44:12
 */
public interface Collectors {

    /**
     * collect (e.g rule,ruleSet,condition,variable)parameter
     * @return
     */
    Collection<Element> collectParameter();

}
