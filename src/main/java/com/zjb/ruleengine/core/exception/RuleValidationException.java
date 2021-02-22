package com.zjb.ruleengine.core.exception;

public class RuleValidationException extends RuleEngineException {

    public RuleValidationException(Throwable cause) {
        super(cause);
    }

    public RuleValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleValidationException(String messgae){
        super(messgae);
    }
}
