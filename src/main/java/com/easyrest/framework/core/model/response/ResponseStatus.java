package com.easyrest.framework.core.model.response;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public enum ResponseStatus {

    SUCCESS("1");

    String code;

    ResponseStatus(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
