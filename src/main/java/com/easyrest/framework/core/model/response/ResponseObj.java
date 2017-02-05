package com.easyrest.framework.core.model.response;

import com.easyrest.framework.core.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class ResponseObj {

    private HttpEntity httpEntity;

    private Object responseObject;

    public ResponseObj(HttpEntity httpEntity, Object responseObject){
        this.httpEntity = httpEntity;
        this.responseObject = responseObject;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public void setHttpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
