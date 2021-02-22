package com.zjb.ruleengine.core.value;

import com.zjb.ruleengine.core.Context;

import java.io.Serializable;
import java.util.Objects;

/**
 * action的执行
 */
public abstract class Function<T,R> implements Serializable {

    private static final long serialVersionUID = -6994254036155752759L;

    /**
     * @param param 参数的map
     * @return
     */
    public abstract R execute(Context context, T param);


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Function)) {
            return false;
        }

        //Function function = (Function) other;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }



}
