package com.easyrest.framework.core.services.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2017/1/21.
 */
public class Session {

    private String email;

    private Map<String, Object> sessionMap = new HashMap<>();

    private long startTime = 0L;

    private static final long expiredTime = 1000 * 60 * 60 * 24 * 7;

    private boolean isLogin = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        startTime = System.currentTimeMillis();
    }

    public Map<String, Object> getSessionMap() {
        startTime = System.currentTimeMillis();
        return sessionMap;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > expiredTime;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
