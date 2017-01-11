package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.services.authentication.api.AuthenticateService;
import com.simpacct.framework.core.services.authentication.impl.AuthenticateServiceImpl;
import com.simpacct.framework.exception.PermissionException;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class PermissionCheckWorkStep extends AbstractWorkStep {

    private AuthenticateService authenticateService = new AuthenticateServiceImpl();

    @Override
    public <T>T executeStep(T entity) {
        permissionCheck(((HttpEntity)entity));
        return entity;
    }

    private void permissionCheck(HttpEntity entity) {
        boolean hasPermissionRequired = false;
        if (authenticateService.authenticate(entity)){
            return;
        }
        if (hasPermissionRequired){
            throw new PermissionException("You have no permission to visit this interface.");
        }
    }

}
