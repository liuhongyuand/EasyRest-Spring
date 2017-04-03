package com.easyrest.framework.core.services.workStep.before.requestCheck.impl;

import com.easyrest.framework.core.annotations.method.RequestMethod;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.before.requestCheck.api.RequestValidateStep;
import com.easyrest.framework.exception.MethodNotAllowedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class RequestValidateWorkStepImpl extends AbstractWorkStep implements RequestValidateStep{

    private static final String NOT_ALLOWED = "%s is not allowed.";

    @Override
    public <T>T executeStep(T entity) {
        validate(((HttpEntity)entity).getAllowedMethods(), ((HttpEntity)entity).getRequest(), (HttpEntity) entity);
        return entity;
    }

    private void validate(List<Class> allowedMethods, HttpServletRequest request, HttpEntity httpEntity){
        String methodName = request.getMethod().toUpperCase();
        for (RequestMethod requestMethod : RequestMethod.values()){
            if (requestMethod.getMethodName().equals(methodName)){
                if (!allowedMethods.contains(requestMethod.getMethodClass())){
                    throw new MethodNotAllowedException(String.format(methodName, NOT_ALLOWED));
                }
            }
        }
    }

}
