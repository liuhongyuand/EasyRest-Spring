package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.annotations.method.RequestMethod;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.exception.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class RequestValidateWorkStep extends AbstractWorkStep {

    @Override
    public <T>T executeStep(T entity) {
        validate(((HttpEntity)entity).getAllowedMethods(), ((HttpEntity)entity).getRequest());
        return entity;
    }

    private void validate(List<Class> allowedMethods, HttpServletRequest request){
        String methodName = request.getMethod().toUpperCase();
        for (RequestMethod requestMethod : RequestMethod.values()){
            if (requestMethod.getMethodName().equals(methodName)){
                if (!allowedMethods.contains(requestMethod.getMethodClass())){
                    throw new MethodNotAllowedException(methodName + " is not allowed");
                }
            }
        }
    }

}
