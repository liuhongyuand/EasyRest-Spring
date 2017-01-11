package com.simpacct.framework.core.services.workStep.api;

import com.simpacct.framework.core.model.HttpEntity;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface WorkStep {

    <T>T executeStep(T t);

}
