package com.easyrest.framework.configuration;

import com.easyrest.framework.core.utils.ImportFileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * This is project parameters
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class GlobalParameters {

    public static final String CALL_BACK_FUNCTION = "callback";
    private static Boolean isDebugMode = true;
    private static String uploadFileDir = System.getProperties().getProperty("user.dir");
    public static String characterEncoding = "UTF-8";
    public static final Map<String, Map<String, String>> i18nMap = new HashMap<>();

    public static void setIsDebugMode(Boolean isDebugMode) {
        GlobalParameters.isDebugMode = isDebugMode;
    }

    public static void setUploadFileDir(String uploadFileDir) {
        GlobalParameters.uploadFileDir = uploadFileDir;
    }

    public static void setCharacterEncoding(String characterEncoding) {
        GlobalParameters.characterEncoding = characterEncoding;
    }

    public static Boolean getIsDebugMode() {
        return isDebugMode;
    }

    public static String getUploadFileDir() {
        return uploadFileDir;
    }

    public static String getCharacterEncoding() {
        return characterEncoding;
    }
}
