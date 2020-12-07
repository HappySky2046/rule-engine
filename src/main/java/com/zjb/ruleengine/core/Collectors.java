package com.zjb.ruleengine.core;

import com.zjb.ruleengine.core.value.Element;

import java.util.Collection;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2020-12-07
 */
public interface Collectors {

    /**
     * collect (e.g rule,ruleSet,condition,variable)parameter
     * @return
     */
    Collection<Element> collectParameter();

}
