package com.easyrest.framework.core.services.workStep.impl;

import com.easyrest.framework.core.annotations.parameter.AllDefined;
import com.easyrest.framework.core.annotations.parameter.NotNull;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.framework.exception.ParameterException;

import java.lang.annotation.Annotation;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class FieldDefinedCheckWorkStep extends AbstractWorkStep {

    @Override
    public <T>T executeStep(T entity) {
        isNeedDefined(((HttpEntity)entity).getRequestModel());
        return entity;
    }

    private void isNeedDefined(RequestModel requestModel) {
        for (Annotation annotation : requestModel.getClass().getAnnotations()) {
            if (annotation.annotationType().equals(AllDefined.class)) {
                if (!requestModel.isAllFieldDefined()) {
                    throw new ParameterException(requestModel.getNotDefinedFields(AllDefined.class));
                }
            }
        }
        if (!requestModel.notNullFieldCheck()){
            throw new ParameterException(requestModel.getNotDefinedFields(NotNull.class));
        }
    }

}
