package com.zjb.ruleengine.core.exception;

/**
 * @author 赵静波
 * @date 2020-07-13 10:11:01
 */
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
