package com.simpacct.framework.exception;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class ConditionMissingException extends RuntimeException {

    public ConditionMissingException(String errorMsg){
        super(errorMsg);
    }

}
