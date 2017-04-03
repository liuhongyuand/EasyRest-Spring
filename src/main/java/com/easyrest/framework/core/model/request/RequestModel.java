package com.easyrest.framework.core.model.request;

import com.easyrest.framework.exception.ConditionMissingException;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
public interface RequestModel {

    boolean notNullFieldCheck();

    boolean isAllFieldDefined();

    String getNotDefinedFields(Class aClass);

    void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException;

    String getFieldValue(String fieldName);

}
