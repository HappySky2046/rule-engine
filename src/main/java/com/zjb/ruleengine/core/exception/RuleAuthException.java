package com.zjb.ruleengine.core.exception;

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
