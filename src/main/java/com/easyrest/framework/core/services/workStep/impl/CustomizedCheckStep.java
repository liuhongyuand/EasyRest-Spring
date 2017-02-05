package com.easyrest.framework.core.services.workStep.impl;

import com.easyrest.framework.core.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/30.
 */
public class CustomizedCheckStep extends AbstractWorkStep {
    @Override
    public <T>T executeStep(T entity) {
        ((HttpEntity)entity).getRequestModel().customizedCheck(((HttpEntity)entity));
        return entity;
    }
}
