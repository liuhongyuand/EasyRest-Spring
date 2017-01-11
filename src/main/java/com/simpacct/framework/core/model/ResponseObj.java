package com.simpacct.framework.core.model;

import com.simpacct.framework.core.model.request.RequestModel;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class ResponseObj {

    private RequestModel requestModel;

    private Object responseObject;

    public ResponseObj(RequestModel requestModel, Object responseObject){
        this.requestModel = requestModel;
        this.responseObject = responseObject;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }
}
