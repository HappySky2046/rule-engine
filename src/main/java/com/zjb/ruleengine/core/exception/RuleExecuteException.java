package com.zjb.ruleengine.core.exception;

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
