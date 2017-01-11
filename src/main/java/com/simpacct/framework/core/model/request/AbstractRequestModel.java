package com.simpacct.framework.core.model.request;

import com.simpacct.framework.core.annotations.parameter.AllDefined;
import com.simpacct.framework.core.annotations.parameter.NotNull;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.utils.StringUtils;
import com.simpacct.framework.exception.ConditionMissingException;

import java.lang.reflect.Field;

/**
 * Created by liuhongyu.louie on 2016/9/30.
 */
public abstract class AbstractRequestModel implements RequestModel {

    @Override
    public boolean notNullFieldCheck(){
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (field.isAnnotationPresent(NotNull.class) && (field.get(this) == null || StringUtils.isEmptyString((String) field.get(this)))) {
                    return false;
                }
            }
        } catch (IllegalAccessException ignored){}
        return true;
    }

    @Override
    public boolean isAllFieldDefined() {
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (field.get(this) == null || StringUtils.isEmptyString((String)field.get(this))) {
                    return false;
                }
            }
        } catch (IllegalAccessException ignored){}
        return true;
    }

    @Override
    public String getNotDefinedFields(Class aClass){
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            for (Field field : this.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (aClass.equals(NotNull.class)){
                    if (field.get(this) == null || StringUtils.isEmptyString((String) field.get(this))) {
                        stringBuilder.append(field.getName()).append(",").append(" ");
                    }
                } else if (aClass.equals(AllDefined.class)) {
                    if (field.get(this) == null || StringUtils.isEmptyString((String) field.get(this))) {
                        stringBuilder.append(field.getName()).append(",").append(" ");
                    }
                }
            }
        } catch (IllegalAccessException ignored){}
        return stringBuilder.toString();
    }

    @Override
    public void customizedCheck(HttpEntity httpEntity) throws ConditionMissingException {}

}
