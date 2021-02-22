package com.zjb.ruleengine.core.exception;

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
