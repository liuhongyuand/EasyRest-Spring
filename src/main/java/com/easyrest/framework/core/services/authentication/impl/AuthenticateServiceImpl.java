package com.easyrest.framework.core.services.authentication.impl;

import com.easyrest.framework.core.annotations.permission.PermissionRequired;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.framework.core.services.authentication.api.AuthenticateService;
import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Override
    public boolean authenticate(HttpEntity httpEntity) {
        RequestModel requestModel = httpEntity.getRequestModel();
        return !requestModel.getClass().isAnnotationPresent(PermissionRequired.class) || EasyRest.getAuthenticateBindingService().checkAuthenticate(httpEntity);
    }

}
