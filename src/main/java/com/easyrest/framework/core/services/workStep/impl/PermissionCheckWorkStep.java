package com.easyrest.framework.core.services.workStep.impl;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.authentication.api.AuthenticateService;
import com.easyrest.framework.core.services.i18n.impl.I18NStaticService;
import com.easyrest.framework.exception.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class PermissionCheckWorkStep extends AbstractWorkStep {

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    public <T>T executeStep(T entity) {
        permissionCheck(((HttpEntity)entity));
        return entity;
    }

    private void permissionCheck(HttpEntity entity) {
        if (!authenticateService.authenticate(entity) || !authenticateService.getSession(entity)){
            throw new PermissionException(I18NStaticService.getMessage(entity, "permission.denied"));
        }
    }

}
