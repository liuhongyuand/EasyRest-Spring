package com.easyrest.framework.core.model.response;

import com.easyrest.framework.core.utils.StringUtils;

/**
 * The exception model
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class ErrorEntity {

    private String code;
    private String message;
    private ErrorInfo data = new ErrorInfo();

    private ErrorEntity(String code, String message, String URL){
        this.setCode(code).setMessage(message).setURL(URL).setExceptionType("Server Error");
    }

    public ErrorEntity(String code, String message, String URL, Exception e){
        this.setCode(code).setMessage(StringUtils.replaceNull(message)).setURL(StringUtils.replaceNull(URL)).setExceptionType(e.getClass().getSimpleName());
    }

    public String getCode() {
        return code;
    }

    private ErrorEntity setCode(String code) {
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

    private ErrorEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getURL() {
        return data.getURL();
    }

    private ErrorEntity setURL(String URL) {
        data.setURL(URL);
        return this;
    }

    public String getExceptionType() {
        return data.getExceptionType();
    }

    private ErrorEntity setExceptionType(String exceptionType) {
        data.setExceptionType(exceptionType);
        return this;
    }

    public static ErrorEntity getExceptionHideErrorInfo(String URL){
        return new ErrorEntity("-1", "server error.", URL);
    }

    private class ErrorInfo{

        private String ExceptionType;
        private String URL;

        String getExceptionType() {
            return ExceptionType;
        }

        void setExceptionType(String exceptionType) {
            ExceptionType = exceptionType;
        }

        String getURL() {
            return URL;
        }

        void setURL(String URL) {
            this.URL = URL;
        }
    }

}
