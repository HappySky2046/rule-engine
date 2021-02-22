package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
public class RuleExecuteException extends RuntimeException{

    public RuleExecuteException(Throwable cause) {
        super(cause);
    }

    public RuleExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleExecuteException(String messgae){
        super(messgae);
    }
}
