package com.easyrest.framework.core.services.workStep.before.customized.impl;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.before.customized.api.CustomizedCheckStep;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2016/12/30.
 */
@Service
public class CustomizedCheckStepImpl extends AbstractWorkStep implements CustomizedCheckStep {
    @Override
    public <T>T executeStep(T entity) {
        ((HttpEntity)entity).getRequestModel().customizedCheck(((HttpEntity)entity));
        return entity;
    }
}
