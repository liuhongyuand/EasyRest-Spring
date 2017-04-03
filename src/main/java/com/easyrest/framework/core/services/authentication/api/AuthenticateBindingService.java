package com.easyrest.framework.core.services.authentication.api;

import com.easyrest.framework.core.model.request.HttpEntity;

/**
 * Created by liuhongyu.louie on 2017/2/5.
 */
public interface AuthenticateBindingService {

    boolean checkAuthenticate(HttpEntity httpEntity);

    String errorMessage();

}
