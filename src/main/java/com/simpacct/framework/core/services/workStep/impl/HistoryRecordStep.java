package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.core.annotations.history.HistoryRequired;
import com.simpacct.framework.core.model.response.ResponseObj;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.model.response.ResponseStatus;
import com.simpacct.framework.core.services.history.api.HistoryService;
import com.simpacct.framework.core.services.workStep.api.WorkStep;
import com.simpacct.framework.core.utils.LogUtils;
import com.simpacct.framework.core.utils.json.JsonTranslation;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
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
                        try {
                            Field field = responseObj.getHttpEntity().getRequestModel().getClass().getDeclaredField(fieldName);
                            field.setAccessible(true);
                            Object value = field.get(responseObj.getHttpEntity().getRequestModel());
                            record.put(fieldName, value);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            LogUtils.error(e.getMessage(), e);
                        }
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
