package com.easyrest.framework.core.services.generateId.impl;

import com.easyrest.framework.core.services.generateId.api.GenerateIdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by liuhongyu.louie on 2017/1/11.
 */
@Service
public class GenerateIdServiceImpl implements GenerateIdService {

    @Override
    public String getId() {
        synchronized (this) {
            return UUID.randomUUID().toString().toUpperCase().replace("-", "");
        }
    }

}
