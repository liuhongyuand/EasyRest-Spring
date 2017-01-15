package com.simpacct.framework.core.services.session;

/**
 * Created by liuhongyu.louie on 2017/1/3.
 */
public enum  SessionParameters {

    UserId("userId"),

    CAPTCHA("captcha_code"),

    ErrorCode("err_code");

    String parameter;

    SessionParameters(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return this.parameter;
    }

}