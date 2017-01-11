package com.simpacct.framework.core.services.authentication.api;

import com.simpacct.framework.core.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface AuthenticateService {

    boolean authenticate(HttpEntity httpEntity);

}
