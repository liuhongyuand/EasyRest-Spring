package com.easyrest.framework.core.services.workStep.after.history.impl;

import com.easyrest.framework.core.annotations.history.HistoryRequired;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.model.response.ResponseStatus;
import com.easyrest.framework.core.services.history.api.HistoryService;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.after.history.api.HistoryRecordStep;
import com.easyrest.framework.core.utils.json.JsonTranslation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhongyu.louie on 2017/1/10.
 */
@Service
public class HistoryRecordStepImpl extends AbstractWorkStep implements HistoryRecordStep {

    private final HistoryService historyService;

    @Autowired
    public HistoryRecordStepImpl(HistoryService historyService) {
        this.historyService = historyService;
    }

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
