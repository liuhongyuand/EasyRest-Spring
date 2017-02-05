package com.easyrest.framework.core.services.session.api;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.session.Session;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuhongyu.louie on 2017/1/21.
 */
public interface SessionService {

    Session createSessionByEmail(HttpEntity httpEntity);

    Session createSessionByEmail(HttpEntity httpEntity, String email);

    void createToken(HttpEntity httpEntity, HttpServletResponse response);

    void findSessionByEmail(String email, HttpEntity httpEntity);

}
