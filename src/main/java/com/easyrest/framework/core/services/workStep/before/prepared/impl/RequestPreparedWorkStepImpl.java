package com.easyrest.framework.core.services.workStep.before.prepared.impl;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.before.prepared.api.RequestPreparedStep;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuhongyu.louie on 2016/12/31.
 */
@Service
public class RequestPreparedWorkStepImpl extends AbstractWorkStep implements RequestPreparedStep {

    @Override
    public <T>T executeStep(T entity) {
        try {
            ((HttpEntity)entity).getRequest().setCharacterEncoding(GlobalParameters.getCharacterEncoding());
        } catch (UnsupportedEncodingException ignored) {}
        return entity;
    }

}
