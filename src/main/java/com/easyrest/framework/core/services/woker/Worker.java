package com.easyrest.framework.core.services.woker;

import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.model.response.ResponseEntity;

import java.util.function.Function;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
public class Worker implements Runnable {

    private Function<ResponseEntity, Void> setResponseEntity;

    private HttpEntity httpEntity;

    private ModelFactory modelFactory;

    public Worker(HttpEntity httpEntity, ModelFactory modelFactory) {
        this.httpEntity = httpEntity;
        this.modelFactory = modelFactory;
    }

    public void setResponseEntity(Function<ResponseEntity, Void> setResponseEntity) {
        this.setResponseEntity = setResponseEntity;
    }

    @Override
    public void run() {
        setResponseEntity.apply((ResponseEntity) modelFactory.getService().doProcess(httpEntity));
    }

}
