package com.easyrest.framework.core.services.woker;

import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.model.request.HttpEntity;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
public class Worker implements Runnable {

    private HttpEntity httpEntity;

    private ModelFactory modelFactory;

    public Worker(HttpEntity httpEntity, ModelFactory modelFactory) {
        this.httpEntity = httpEntity;
        this.modelFactory = modelFactory;
    }

    @Override
    public void run() {
        modelFactory.getService().doProcess(httpEntity);
    }

}
