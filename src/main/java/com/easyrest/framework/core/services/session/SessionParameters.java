package com.easyrest.framework.core.services.session;

/**
 * Created by liuhongyu.louie on 2017/1/3.
 */
public enum  SessionParameters {

    TOKEN("token"),

    UserId("user_id"),

    UserEmail("email"),

    EnterpriseId("enterprise_id"),

    CAPTCHA("captcha_code"),

    I18NID("I18N_id"),

    ErrorCode("err_code");

    String parameter;

    SessionParameters(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return this.parameter;
    }

}
