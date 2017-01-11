package com.simpacct.framework.core.services.authentication.impl;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.services.authentication.api.AuthenticateService;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class AuthenticateServiceImpl implements AuthenticateService {

    @Override
    public boolean authenticate(HttpEntity httpEntity) {
        return true;
    }
}
