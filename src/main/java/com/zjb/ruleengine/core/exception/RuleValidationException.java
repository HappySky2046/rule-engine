package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
public class RuleValidationException extends RuleEngineException {

    public RuleValidationException(Throwable cause) {
        super(cause);
    }

    public RuleValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleValidationException(String messgae) {
        super(messgae);
    }
}
