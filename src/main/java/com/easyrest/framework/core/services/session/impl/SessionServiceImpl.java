package com.easyrest.framework.core.services.session.impl;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.generateId.impl.GenerateIdStaticService;
import com.easyrest.framework.core.services.session.Session;
import com.easyrest.framework.core.services.session.SessionParameters;
import com.easyrest.framework.core.services.session.SessionSupport;
import com.easyrest.framework.core.services.session.api.SessionService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuhongyu.louie on 2017/1/21.
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public Session createSessionByEmail(HttpEntity httpEntity, String email) {
        try {
            if (email == null) {
                return new Session();
            }
            Session session = SessionSupport.SESSION_MAP.get(email);
            if (session == null) {
                session = new Session();
                session.setEmail(email);
                SessionSupport.SESSION_MAP.put(session.getEmail(), session);
                httpEntity.setSession(session);
            }
            return session;
        } catch (Exception ignored){
            return new Session();
        }
    }

    @Override
    public Session createSessionByEmail(HttpEntity httpEntity) {
        try {
            if (httpEntity.getParameters().get(SessionParameters.UserEmail.getParameter()) == null) {
                return new Session();
            }
            Object valueObj = httpEntity.getParameters().get(SessionParameters.UserEmail.getParameter());
            if (valueObj == null) {
                return new Session();
            }
            String emailValue;
            if (valueObj instanceof String[]){
                emailValue = ((String[])valueObj)[0];
            } else if (valueObj instanceof String){
                emailValue = String.valueOf(valueObj);
            } else {
                return new Session();
            }
            return createSessionByEmail(httpEntity, emailValue);
        } catch (Exception ignored){
            return new Session();
        }
    }

    @Override
    public void createToken(HttpEntity httpEntity, HttpServletResponse response) {
        Cookie cookie = new Cookie(SessionParameters.TOKEN.getParameter(), GenerateIdStaticService.createId());
        cookie.setPath("/");
        cookie.setDomain("localhost");
        Session session = getSession(httpEntity);
        String oldToken = session.getEmail();
        session.setEmail(cookie.getValue());
        SessionSupport.SESSION_MAP.put(cookie.getValue(), session);
        SessionSupport.SESSION_MAP.remove(oldToken);
        response.addCookie(cookie);
    }

    private Session getSession(HttpEntity httpEntity){
        Session session = httpEntity.getSession();
        if (session == null){
            return new Session();
        }
        return session;
    }

    @Override
    public void findSessionByEmail(String email, HttpEntity httpEntity){
        if (SessionSupport.SESSION_MAP.get(email) != null){
            httpEntity.setSession(SessionSupport.SESSION_MAP.get(email));
        }
    }

}
