package com.easyrest.framework.core.services.workStep.impl;

import com.easyrest.framework.core.annotations.method.RequestMethod;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.i18n.impl.I18NStaticService;
import com.easyrest.framework.exception.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class RequestValidateWorkStep extends AbstractWorkStep {

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
                    throw new MethodNotAllowedException(String.format(I18NStaticService.getMessage(httpEntity, "not.allowed"), methodName));
                }
            }
        }
    }

}
