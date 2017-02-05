package com.easyrest.framework.core.services.session;

import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.services.session.impl.SessionStaticService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2017/1/3.
 */
public class SessionSupport {

    public static final Map<String, Session> SESSION_MAP = new HashMap<>();

    public static boolean put(HttpEntity httpEntity, SessionParameters key, Object value) {
        return put(httpEntity, key.getParameter(), value);
    }

    public static boolean put(HttpEntity httpEntity, String key, Object value) {
        Map<String, Object> session = getSessionMap(httpEntity);
        session.put(key, value);
        return session.get(key) != null && session.get(key).equals(value);
    }

    public static Object get(HttpEntity httpEntity, SessionParameters parameters) {
        return get(httpEntity, parameters.getParameter());
    }

    public static Object get(HttpEntity httpEntity, SessionParameters parameters, boolean isDelete) {
        return get(httpEntity, parameters.getParameter(), isDelete);
    }

    public static Object get(HttpEntity httpEntity, String key) {
        return get(httpEntity, key, false);
    }

    public static Object get(HttpEntity httpEntity, String key, boolean isDelete) {
        Map<String, Object> session = getSessionMap(httpEntity);
        Object result = session.get(key);
        if (isDelete) {
            session.remove(key);
        }
        return result;
    }

    public static void delete(HttpEntity httpEntity, SessionParameters parameters) {
        delete(httpEntity, parameters.getParameter());
    }

    public static void delete(HttpEntity httpEntity, String key) {
        Session session = httpEntity.getSession();
        session.getSessionMap().remove(key);
    }

    private static Map<String, Object> getSessionMap(HttpEntity httpEntity){
        if (httpEntity.getSession() != null) {
            return httpEntity.getSession().getSessionMap();
        }
        return SessionStaticService.getService().createSessionByEmail(httpEntity).getSessionMap();
    }

}
