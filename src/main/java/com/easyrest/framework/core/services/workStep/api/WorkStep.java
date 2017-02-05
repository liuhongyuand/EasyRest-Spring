package com.easyrest.framework.core.services.workStep.api;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public interface WorkStep {

    <T>T executeStep(T t);

}
