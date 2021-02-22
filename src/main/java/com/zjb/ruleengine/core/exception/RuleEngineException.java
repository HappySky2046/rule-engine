package com.zjb.ruleengine.core.exception;

public class RuleEngineException extends RuntimeException
{
    public RuleEngineException(Throwable cause) {
        super(cause);
    }

    public RuleEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleEngineException(String messgae){
        super(messgae);
    }
}
