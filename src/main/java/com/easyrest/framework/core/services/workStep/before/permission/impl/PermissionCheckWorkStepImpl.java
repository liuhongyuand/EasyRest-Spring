package com.easyrest.framework.core.services.workStep.before.permission.impl;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.authentication.api.AuthenticateService;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.before.permission.api.PermissionCheckStep;
import com.easyrest.framework.easyrest.EasyRest;
import com.easyrest.framework.exception.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class PermissionCheckWorkStepImpl extends AbstractWorkStep implements PermissionCheckStep {

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    public <T>T executeStep(T entity) {
        permissionCheck(((HttpEntity)entity));
        return entity;
    }

    private void permissionCheck(HttpEntity entity) {
        if (!authenticateService.authenticate(entity)){
            throw new PermissionException(EasyRest.getAuthenticateBindingService().errorMessage());
        }
    }

}
