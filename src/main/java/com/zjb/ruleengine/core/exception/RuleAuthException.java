package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
public class RuleAuthException extends RuntimeException{

    public RuleAuthException(Throwable cause) {
        super(cause);
    }

    public RuleAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleAuthException(String messgae){
        super(messgae);
    }

}
