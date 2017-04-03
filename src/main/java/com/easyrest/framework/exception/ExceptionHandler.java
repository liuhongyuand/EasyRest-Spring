package com.easyrest.framework.exception;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.controllers.SystemEntranceController;
import com.easyrest.framework.core.model.ErrorEntity;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.easyrest.EasyRest;
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
    Object responseErrorPage(ExceptionHandler exception){
        if (EasyRest.getExceptionBindingService() != null){
            EasyRest.getExceptionBindingService().exception(exception.request, exception.response, exception.e);
        }
        return LogUtils.printGsonObject(getReturnExceptionInfo(exception));
    }

    private Object getReturnExceptionInfo(ExceptionHandler exception){
        if (!GlobalParameters.getIsDebugMode()){
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
        return new ErrorEntity("", "resource not found.", request.getRequestURL().toString(), e);
    }

    private Object responseServerError(){
        return new ErrorEntity("", e.getMessage(), request.getRequestURL().toString(), e);
    }

    private ResponseEntity responseParameterError(){
        RESPONSE_ENTITY.setFailed(String.format("%s not defined.", e.getMessage()));
        return RESPONSE_ENTITY;
    }

    private Exception getExceptionType() {
        return e;
    }

}
