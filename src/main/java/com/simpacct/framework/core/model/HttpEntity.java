package com.simpacct.framework.core.model;

import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;
import com.simpacct.framework.configuration.GlobalParameters;
import com.simpacct.framework.core.model.request.RequestModel;
import com.simpacct.framework.core.utils.json.JsonTranslation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * This is the http entity for request.
 * Created by liuhongyu.louie on 2016/9/29.
 */
public class HttpEntity {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestModel requestModel;
    private List<Class> allowedMethods;

    public HttpEntity(HttpServletRequest request, HttpServletResponse response, RequestModel requestModel, List<Class> allowedMethods){
        this.request = request;
        this.response = response;
        this.requestModel = requestModel;
        this.allowedMethods = allowedMethods;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public List<Class> getAllowedMethods() {
        return allowedMethods;
    }

    public Map<?, ?> getRequestMap() {
        return getWholeParametersToMap();
    }

    private Map getWholeParametersToMap(){
        StringBuilder stringBuffer = new StringBuilder("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), GlobalParameters.characterEncoding));
            String line;
            while((line = br.readLine())!=null){
                stringBuffer.append(line);
            }
            Map parametersMapFromJson = JsonTranslation.jsonString2Object(stringBuffer.toString(), LinkedHashTreeMap.class);
            Map parametersMap = new LinkedTreeMap();
            Map map = request.getParameterMap();
            if (parametersMapFromJson != null) {
                parametersMapFromJson.forEach((k, v) -> {
                    if (v.toString().endsWith(".0")){
                        v = String.valueOf(v).substring(0, v.toString().length() - 2);
                    }
                    parametersMap.put(k, v);
                });
            }
            map.forEach(parametersMap::put);
            return parametersMap;
        } catch (IOException ignored) {}
        return null;
    }
}
