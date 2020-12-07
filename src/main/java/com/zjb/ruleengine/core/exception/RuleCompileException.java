package com.zjb.ruleengine.core.exception;

public class RuleCompileException extends RuntimeException{

    public RuleCompileException(Throwable cause) {
        super(cause);
    }

    public RuleCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleCompileException(String messgae){
        super(messgae);
    }

}
