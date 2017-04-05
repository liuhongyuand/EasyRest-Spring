package com.easyrest.framework.core.model.request;

import com.easyrest.framework.configuration.GlobalParameters;
import com.easyrest.framework.core.model.TransactionContext;
import com.easyrest.framework.core.utils.json.JsonTranslation;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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
    private TransactionContext transactionContext;
    private Map<Object, Object> parameters;

    public HttpEntity(HttpServletRequest request, HttpServletResponse response, RequestModel requestModel, List<Class> allowedMethods){
        this.request = request;
        this.response = response;
        this.requestModel = requestModel;
        this.allowedMethods = allowedMethods;
    }

    public Map<Object, Object> getParameters(){
        return parameters;
    }

    public TransactionContext getTransactionContext() {
        return transactionContext;
    }

    public void setTransactionContext(TransactionContext transactionContext) {
        this.transactionContext = transactionContext;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), GlobalParameters.getCharacterEncoding()));
            String line;
            while((line = br.readLine())!=null){
                stringBuffer.append(line);
            }
            Map parametersMapFromJson = isJson(stringBuffer.toString()) ? JsonTranslation.jsonString2Object(stringBuffer.toString(), LinkedHashTreeMap.class) : getMapFromString(stringBuffer.toString());
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
            this.parameters = parametersMap;
            return parametersMap;
        } catch (IOException ignored) {}
        return null;
    }

    private boolean isJson(String value){
        return value.startsWith("{") && value.endsWith("}");
    }

    private Map getMapFromString(String value){
        if (value.startsWith("\"") && value.endsWith("\"")){
            value = value.substring(1, value.length() - 1);
        }
        Map<String, String> valueMap = new HashMap<>();
        if (value.contains("&")) {
            String[] parameters = value.split("&");
            for (String parameter : parameters) {
                if (parameter.contains("=")) {
                    valueMap.put(parameter.split("=")[0], parameter.split("=")[1]);
                }
            }
        }
        return valueMap;
    }

}
