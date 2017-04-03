package com.easyrest.framework.core.services.workStep.before.fieldInject.impl;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.annotations.parameter.SkipInject;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.model.request.RequestModel;
import com.easyrest.framework.core.services.workStep.AbstractWorkStep;
import com.easyrest.framework.core.services.workStep.before.fieldInject.api.ValueInjectStep;
import com.easyrest.framework.core.utils.json.JsonTranslation;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
@Service
public class ValueInjectWorkStepImpl extends AbstractWorkStep implements ValueInjectStep {

    @Override
    public <T>T executeStep(T entity) {
        if (((HttpEntity)entity).getRequestModel().getClass().isAnnotationPresent(SkipInject.class) || ((HttpEntity)entity).getRequestModel().getClass().getDeclaredFields().length == 0){
            return entity;
        }
        return injectValue(entity, ((HttpEntity)entity).getRequestModel());
    }

    private <T>T injectValue(T entity, RequestModel requestModel){
        Set<Field> fieldSet = new LinkedHashSet<>();
        Set<Field> deleteSet = new LinkedHashSet<>();
        for(Field field : requestModel.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if (field.getType().equals(String.class)){
                try {
                    if (field.get(requestModel) == null && field.getType().equals(String .class)) {
                        field.set(requestModel, "");
                    }
                } catch (IllegalAccessException ignored) {}
            }
            fieldSet.add(field);
        }
        Map<?, ?> requestMap = ((HttpEntity)entity).getRequestMap();
        for (Map.Entry<?, ?> entry : requestMap.entrySet()){
            for (Field field: fieldSet){
                if (field.getName().equalsIgnoreCase(String.valueOf(entry.getKey()))){
                    try {
                        String value;
                        try {
                            value = URLDecoder.decode(getValue(entry.getValue()), GlobalParameters.getCharacterEncoding());
                        } catch (Exception ignored){
                            value = getValue(entry.getValue());
                        }
                        field.set(requestModel, value);
                    } catch (IllegalAccessException ignored) {}
                    deleteSet.add(field);
                }
            }
            fieldSet.removeAll(deleteSet);
            deleteSet.clear();
        }
        return entity;
    }

    private String getValue(Object value){
        if (value instanceof String[]){
            return toJsonValue(((String[]) value)[0]);
        } else {
            return toJsonValue(value);
        }
    }

    private String toJsonValue(Object obj){
        String result = JsonTranslation.object2JsonString(obj);
        if (result.startsWith("\"") && result.endsWith("\"")){
            return result.substring(1, result.length() - 1);
        }
        return result;
    }

}
