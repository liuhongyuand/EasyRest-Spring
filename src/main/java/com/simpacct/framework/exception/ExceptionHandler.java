package com.simpacct.framework.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.simpacct.framework.configuration.GlobalParameters;
import com.simpacct.framework.core.controllers.SystemEntranceController;
import com.simpacct.framework.core.model.ErrorEntity;
import com.simpacct.framework.core.model.response.ResponseEntity;
import com.simpacct.framework.core.utils.LogUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Catch error and exceptions.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@ControllerAdvice
public class ExceptionHandler extends Exception {

    private static final ResponseEntity<String> RESPONSE_ENTITY = new ResponseEntity<>();

    public ExceptionHandler(){}

    @org.springframework.web.bind.annotation.ExceptionHandler(ExceptionHandler.class)
    public @ResponseBody
    @JsonSerialize
    Object responseErrorPage(ExceptionHandler exception){
        return LogUtils.printGsonObject(getReturnExceptionInfo(exception));
    }

    private Object getReturnExceptionInfo(ExceptionHandler exception){
        if (!GlobalParameters.isDebugMode){
            return ErrorEntity.getExceptionHideErrorInfo("");
        }
        if (exception.getExceptionType().getClass().equals(PageNotFoundException.class)){
            return exception.responseErrorPage();
        } else if (exception.getExceptionType().getClass().equals(ParameterException.class)){
            return exception.responseParameterError();
        } else {
            return exception.responseServerError();
        }
    }

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Exception e;

    public ExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        this.request = request;
        this.response = response;
        this.e = e;
        SystemEntranceController.responseHeaderConfigurations(response);
    }

    private Object responseErrorPage(){
        return new ErrorEntity("-1", "The resource is not found", request.getRequestURL().toString(), e);
    }

    private Object responseServerError(){
        return new ErrorEntity("-1", e.getMessage(), request.getRequestURL().toString(), e);
    }

    private ResponseEntity responseParameterError(){
        RESPONSE_ENTITY.setFailed("ParameterError: " + e.getMessage() + "has not defined.");
        return RESPONSE_ENTITY;
    }

    private Exception getExceptionType() {
        return e;
    }

}
