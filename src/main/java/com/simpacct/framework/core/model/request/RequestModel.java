package com.simpacct.framework.core.model.request;

import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.exception.ConditionMissingException;

/**
 * Created by liuhongyu.louie on 2016/12/27.
 */
public interface RequestModel {

    boolean notNullFieldCheck();

    boolean isAllFieldDefined();

    String getNotDefinedFields(Class aClass);

    void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException;

}
