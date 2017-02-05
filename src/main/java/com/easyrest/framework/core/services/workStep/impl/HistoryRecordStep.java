package com.easyrest.framework.core.services.workStep.impl;

import com.easyrest.framework.core.annotations.history.HistoryRequired;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.model.response.ResponseStatus;
import com.easyrest.framework.core.services.history.api.HistoryService;
import com.easyrest.framework.core.services.workStep.api.WorkStep;
import com.easyrest.framework.core.utils.json.JsonTranslation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

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
            if (ResponseStatus.SUCCESS.getCode().equals(responseEntity.getCode()) && responseObj.getHttpEntity().getRequestModel().getClass().isAnnotationPresent(HistoryRequired.class)){
                HistoryRequired historyRequired = responseObj.getHttpEntity().getRequestModel().getClass().getAnnotation(HistoryRequired.class);
                if (historyRequired.value().length < 1) {
                    historyService.record(JsonTranslation.object2JsonString(responseObj.getHttpEntity().getRequestModel()));
                } else {
                    Map<String, Object> record = new HashMap<>();
                    for (String fieldName : historyRequired.value()) {
                        record.put(fieldName, responseObj.getHttpEntity().getRequestModel().getFieldValue(fieldName));
                    }
                    if (record.size() > 0) {
                        historyService.record(JsonTranslation.object2JsonString(record));
                    }
                }
            }
        }
        return t;
    }
}
