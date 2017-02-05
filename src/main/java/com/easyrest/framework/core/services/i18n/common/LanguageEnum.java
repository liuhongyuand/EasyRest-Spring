package com.easyrest.framework.core.services.i18n.common;

/**
 * Created by liuhongyu.louie on 2017/1/14.
 */
public enum LanguageEnum{

    DEFAULT(1, "zh"),

    CHINESE(1, "zh"),

    ENGLISH(2, "en");

    int languageId;

    String languageType;

    LanguageEnum(int languageId, String languageType){
        this.languageId = languageId;
        this.languageType = languageType;
    }

    public String getLanguageType(){
        return this.languageType;
    }

    public int getLanguageId(){
        return this.languageId;
    }

}
