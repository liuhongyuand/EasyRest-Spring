package com.easyrest.framework.core.services.generateId.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by liuhongyu.louie on 2017/1/21.
 */
@Component
public class GenerateIdStaticService {

    @Autowired
    private GenerateIdService generateIdService;

    private static GenerateIdService staticService;

    private GenerateIdStaticService(){}

    public static String createId(){
        return staticService.getId();
    }

    @PostConstruct
    private void setStaticService(){
        staticService = generateIdService;
    }

}
