package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
public class RuleLoadException extends RuntimeException{

    public RuleLoadException(Throwable cause) {
        super(cause);
    }

    public RuleLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleLoadException(String messgae){
        super(messgae);
    }

}
