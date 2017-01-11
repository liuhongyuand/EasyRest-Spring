package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.annotations.history.HistoryRequired;
import com.simpacct.framework.core.model.ResponseObj;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.model.response.ResponseStatus;
import com.simpacct.framework.core.services.history.api.HistoryService;
import com.simpacct.framework.core.services.workStep.api.WorkStep;
import com.simpacct.framework.core.utils.json.JsonTranslation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
public class HistoryRecordStep implements WorkStep {

    @Autowired
    private HistoryService historyService;

    @Override
    public <T> T executeStep(T t) {
        ResponseObj responseObj = (ResponseObj) t;
        if (((ResponseObj) t).getResponseObject() instanceof ResponseEntity){
            ResponseEntity responseEntity = (ResponseEntity) ((ResponseObj) t).getResponseObject();
            if (ResponseStatus.SUCCESS.getCode().equals(responseEntity.getCode()) && responseObj.getRequestModel().getClass().isAnnotationPresent(HistoryRequired.class)){
                historyService.record(JsonTranslation.object2JsonString(responseObj.getRequestModel()));
            }
        }
        return t;
    }
}
