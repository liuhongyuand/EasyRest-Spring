package com.simpacct.framework.core.model;

import com.simpacct.framework.core.utils.StringUtils;

/**
 * The exception model
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class ErrorEntity {

    private String Code;
    private String Message;
    private String ExceptionType;
    private String URL;

    public ErrorEntity(String Code, String Message, String URL){
        this.setCode(Code).setMessage(Message).setURL(URL).setExceptionType("Server Error");
    }

    public ErrorEntity(String Code, String Message, String URL, Exception e){
        this.setCode(StringUtils.replaceNull(Code)).setMessage(StringUtils.replaceNull(Message)).setURL(StringUtils.replaceNull(URL)).setExceptionType(e.getClass().getSimpleName());
    }

    public String getCode() {
        return Code;
    }

    public ErrorEntity setCode(String code) {
        Code = code;
        return this;
    }

    public String getMessage() {
        return Message;
    }

    public ErrorEntity setMessage(String message) {
        Message = message;
        return this;
    }

    public String getURL() {
        return URL;
    }

    public ErrorEntity setURL(String URL) {
        this.URL = URL;
        return this;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public ErrorEntity setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
        return this;
    }

    public static ErrorEntity getExceptionHideErrorInfo(String URL){
        return new ErrorEntity("-1", "Something has wrong, please try again.", URL);
    }

}
