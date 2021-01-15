
package com.zjb.ruleengine.core.condition;

import com.zjb.ruleengine.core.Collectors;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.Weight;
import com.zjb.ruleengine.core.value.Element;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 赵静波
 * @date 2020-09-30 14:06:40
 */
public abstract class AbstractCondition implements Serializable, Weight, Collectors {
    /**
     * id，可用于标识
     */
    private String id;

    /**
     * @return boolean
     * @Author zjb
     * @Description 执行条件，返回布尔值
     * @Date 22:05 2019-06-23
     * @Param context
     **/
    public abstract boolean evaluate(Context context);

    /**
     * build ,可以优化condition的执行顺序
     *
     * @return
     */
    public AbstractCondition build() {
        return this;
    }

    @Override
    public abstract Collection<Element> collectParameter();

    @Override
    public abstract int getWeight();

    public AbstractCondition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}