package com.simpacct.framework.core.services.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuhongyu.louie on 2017/1/3.
 */
public class SessionSupport {

    public static boolean put(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        return session.getAttribute(key) != null && session.getAttribute(key).equals(value);
    }

    public static Object get(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }
}
