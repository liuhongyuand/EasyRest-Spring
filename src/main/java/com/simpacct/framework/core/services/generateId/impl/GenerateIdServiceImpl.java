package com.simpacct.framework.core.services.generateId.impl;

import com.simpacct.framework.core.services.generateId.api.GenerateIdService;

import java.util.UUID;

/**
 * Created by liuhongyu.louie on 2017/1/11.
 */
public class GenerateIdServiceImpl implements GenerateIdService {

    @Override
    public String getId() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

}
