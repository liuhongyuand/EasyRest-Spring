package com.easyrest.framework.core.model;

import com.easyrest.framework.core.services.i18n.impl.I18NStaticService;
import com.easyrest.framework.core.utils.StringUtils;

/**
 * The exception model
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class ErrorEntity {

    private String code;
    private String message;
    private ErrorInfo data = new ErrorInfo();

    public ErrorEntity(String code, String message, String URL){
        this.setCode(code).setMessage(message).setURL(URL).setExceptionType("Server Error");
    }

    public ErrorEntity(String code, String message, String URL, Exception e){
        this.setCode(code).setMessage(StringUtils.replaceNull(message)).setURL(StringUtils.replaceNull(URL)).setExceptionType(e.getClass().getSimpleName());
    }

    public String getCode() {
        return code;
    }

    public ErrorEntity setCode(String code) {
        if (StringUtils.isEmptyString(code)){
            this.code = "-1";
        } else {
            this.code = code;
        }
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getURL() {
        return data.getURL();
    }

    public ErrorEntity setURL(String URL) {
        data.setURL(URL);
        return this;
    }

    public String getExceptionType() {
        return data.getExceptionType();
    }

    public ErrorEntity setExceptionType(String exceptionType) {
        data.setExceptionType(exceptionType);
        return this;
    }

    public static ErrorEntity getExceptionHideErrorInfo(String URL){
        return new ErrorEntity("-1", I18NStaticService.getMessage("unknown.error"), URL);
    }

    private class ErrorInfo{

        private String ExceptionType;
        private String URL;

        public String getExceptionType() {
            return ExceptionType;
        }

        public void setExceptionType(String exceptionType) {
            ExceptionType = exceptionType;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }

}
