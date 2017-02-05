package com.easyrest.framework.core.services.session.impl;

import com.easyrest.framework.core.services.session.api.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by liuhongyu.louie on 2017/1/21.
 */
@Component
public class SessionStaticService {

    @Autowired
    private SessionService sessionService;

    private static SessionService staticService;

    public static SessionService getService(){
        return staticService;
    }

    @PostConstruct
    private void setStaticService(){
        staticService = sessionService;
    }

}
