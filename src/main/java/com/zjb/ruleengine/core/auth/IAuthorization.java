package com.zjb.ruleengine.core.auth;

import com.zjb.ruleengine.core.Context;

/**
 * 权限认证接口
 */
public abstract class IAuthorization {

    private String id;

    /**
     * 认证是否通过
     *
     * @param context
     * @return
     */
    public abstract boolean authorization(Context context);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
