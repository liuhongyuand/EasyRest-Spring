package com.simpacct.framework.configuration;

import com.simpacct.framework.core.utils.ImportFileUtils;

/**
 * This is project parameters
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class GlobalParameters {

    public static final String CALL_BACK_FUNCTION = "callback";
    public static final Boolean isDebugMode = Boolean.valueOf(ImportFileUtils.getPropertiesParameters().getProperty("isDebugMode"));
    public static final String uploadFileDir = String.valueOf(ImportFileUtils.getPropertiesParameters().getProperty("uploadFileDir"));
    public static final String characterEncoding = String.valueOf(ImportFileUtils.getPropertiesParameters().getProperty("characterEncoding"));

}
