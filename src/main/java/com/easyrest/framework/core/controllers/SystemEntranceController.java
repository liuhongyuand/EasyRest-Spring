package com.easyrest.framework.core.controllers;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.configuration.ResponseConfiguration;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.image.util.ImageSupport;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.core.utils.StringUtils;
import com.easyrest.framework.core.utils.json.JsonTranslation;
import com.easyrest.framework.exception.ExceptionHandler;
import com.easyrest.framework.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.easyrest.framework.core.utils.StringUtils.isEmptyString;

/**
 * The entrance of this data interface.
 * Created by liuhongyu.louie on 2016/10/1.
 * Email: liuhongyu.louie@gmail.com
 */
@RestController
@EnableWebMvc
@Configuration
@RequestMapping(value = {RequestPath.System.REQUEST_ENTRANCE})
public class SystemEntranceController {

    @Autowired
    private RequestServiceEntranceController requestDispatcher;

    @RequestMapping(value = {RequestPath.System.REST + RequestPath.System.REQUEST_ENTRANCE}, produces = {ResponseConfiguration.JSON})
    public @ResponseBody
    Object Start(HttpServletRequest request, HttpServletResponse response) throws ExceptionHandler {
        ResponseObj responseObj;
        try {
            LogUtils.info(request.getMethod() + ": " + request.getRequestURL().toString(), this.getClass());
            responseObj = requestDispatcher.requestDispatcher(request, response);
            responseHeaderConfigurations(responseObj.getHttpEntity(), response);
            return JsonAndJsonpSupport.toJsonOrJsonp(responseObj.getResponseObject(), request, response);
        }catch (Exception e){
            throw new ExceptionHandler(request, response, e);
        }finally {
            rollbackConfigurations(request, response);
        }
    }

    @RequestMapping(value = {RequestPath.System.IMAGE + RequestPath.System.REQUEST_ENTRANCE}, produces = {MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    Object Support(HttpServletRequest request, HttpServletResponse response) {
        ResponseObj responseObj;
        try {
            responseObj = requestDispatcher.requestDispatcher(request, response);
            responseHeaderConfigurations(responseObj.getHttpEntity(), response);
            return responseObj.getResponseObject();
        }catch (Exception e){
            return ImageSupport.getWhiteImage();
        }
    }

    @RequestMapping(value = {RequestPath.System.REQUEST_ENTRANCE}, produces = {ResponseConfiguration.JSON})
    public @ResponseBody
    Object NotFound(HttpServletRequest request, HttpServletResponse response) throws ExceptionHandler {
        throw new ExceptionHandler(request, response, new PageNotFoundException());
    }

    private void rollbackConfigurations(HttpServletRequest request, HttpServletResponse response) {
        LogUtils.debug("Complete " + request.getRequestURI() + " request.", this.getClass());
    }

    public static void responseHeaderConfigurations(HttpServletResponse response) {
        for(ResponseConfiguration.Header header : ResponseConfiguration.Header.values()){
            if (response != null) {
                response.setHeader(header.getEntry().getKey(), header.getEntry().getValue());
            }
        }
    }

    private static void responseHeaderConfigurations(HttpEntity httpEntity, HttpServletResponse response) {
        responseHeaderConfigurations(response);
        if (response != null){
            if (httpEntity.getRequest().getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
        }
    }

    private static class JsonAndJsonpSupport{

        static Object toJsonOrJsonp(Object src, HttpServletRequest request, HttpServletResponse response){
            if (src instanceof ResponseEntity) {
                response.setHeader(HttpHeaders.CONTENT_TYPE, ResponseConfiguration.JSON);
                StringBuilder callback = new StringBuilder(StringUtils.replaceNull(request.getParameter(GlobalParameters.CALL_BACK_FUNCTION)));
                return format(isEmptyString(callback.toString()) ? JsonTranslation.object2JsonString(src) : callback.append("(").append(JsonTranslation.object2JsonString(src)).append(");").toString());
            } else if (src instanceof byte[]){
                response.setHeader(HttpHeaders.CONTENT_TYPE, ResponseConfiguration.IMAGE);
            }
            return src;
        }

        private static Object format(String gson){
            return gson;
        }

    }

}
