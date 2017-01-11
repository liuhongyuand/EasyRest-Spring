package com.simpacct.framework.core.services.workStep.impl;

import com.simpacct.framework.configuration.GlobalParameters;
import com.simpacct.framework.core.model.file.FileUploadModel;
import com.simpacct.framework.core.model.request.RequestModel;
import com.simpacct.framework.core.model.HttpEntity;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by liuhongyu.louie on 2016/12/29.
 */
public class ValueInjectWorkStep extends AbstractWorkStep {

    @Override
    public <T>T executeStep(T entity) {
        if (((HttpEntity)entity).getRequestModel() instanceof FileUploadModel){
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
                    field.set(requestModel, "");
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
                            value = URLDecoder.decode(getValue(entry.getValue()), GlobalParameters.characterEncoding);
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
            return String.valueOf(((String[]) value)[0]);
        } else {
            return String.valueOf(value);
        }
    }

}
