package com.easyrest.framework.core.services.business.api;

import com.easyrest.framework.core.model.HttpEntity;

/**
 * The interface of the controllers.
 * Created by liuhongyu.louie on 2016/9/29.
 */
public interface RequestProcessService {

    Object doProcess(HttpEntity httpEntity);

}
