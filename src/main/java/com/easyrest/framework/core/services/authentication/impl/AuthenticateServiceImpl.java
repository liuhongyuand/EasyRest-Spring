package com.easyrest.framework.core.services.authentication.impl;

import com.easyrest.framework.core.annotations.permission.PermissionRequired;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.framework.core.services.authentication.api.AuthenticateService;
import com.easyrest.framework.core.services.session.Session;
import com.easyrest.framework.core.services.session.SessionParameters;
import com.easyrest.framework.core.services.session.SessionSupport;
import com.easyrest.framework.core.services.session.impl.SessionStaticService;
import com.easyrest.framework.easyrest.EasyRest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Override
    public boolean authenticate(HttpEntity httpEntity) {
        RequestModel requestModel = httpEntity.getRequestModel();
        if (!requestModel.getClass().isAnnotationPresent(PermissionRequired.class)) {
            return true;
        } else {
            EasyRest.getAuthenticateBindingService().checkAuthenticate(httpEntity);
        }
        return false;
    }

    @Override
    public boolean getSession(HttpEntity httpEntity) {
        Map<Object, Object> objectMap = httpEntity.getParameters();
        if (objectMap == null){
            return true;
        }
        Object valueObj = objectMap.get(SessionParameters.UserEmail.getParameter());
        if (valueObj == null) {
            return true;
        }
        String emailValue;
        if (valueObj instanceof String[]){
            emailValue = ((String[])valueObj)[0];
        } else if (valueObj instanceof String){
            emailValue = String.valueOf(valueObj);
        } else {
            return true;
        }
        Session session = SessionSupport.SESSION_MAP.get(emailValue);
        if (session != null){
            httpEntity.setSession(session);
        } else {
            SessionStaticService.getService().createSessionByEmail(httpEntity, emailValue);
        }
        return true;
    }

}
