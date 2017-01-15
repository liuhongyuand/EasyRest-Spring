package com.simpacct.framework.core.services.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuhongyu.louie on 2017/1/3.
 */
public class SessionSupport {

    public static boolean put(HttpServletRequest request, SessionParameters key, Object value) {
        return put(request, key.getParameter(), value);
    }

    public static boolean put(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return session.getAttribute(key) != null && session.getAttribute(key).equals(value);
    }

    public static Object get(HttpServletRequest request, SessionParameters parameters) {
        return get(request, parameters.getParameter());
    }

    public static Object get(HttpServletRequest request, String key) {
        return get(request, key, true);
    }

    public static Object get(HttpServletRequest request, String key, boolean isDelete) {
        HttpSession session = request.getSession();
        Object result = session.getAttribute(key);
        if (isDelete) {
            session.removeAttribute(key);
        }
        return result;
    }
}
