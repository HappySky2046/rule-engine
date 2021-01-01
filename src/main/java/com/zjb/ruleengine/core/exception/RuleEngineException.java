package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
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
