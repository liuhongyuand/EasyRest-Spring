package com.simpacct.framework.core.controllers;

import com.simpacct.business.configuration.RequestPath;
import com.simpacct.business.configuration.RestConfig;
import com.simpacct.framework.core.model.HttpEntity;
import com.simpacct.framework.core.model.ModelFactory;
import com.simpacct.framework.core.model.ResponseObj;
import com.simpacct.framework.core.services.workStep.api.WorkStep;
import com.simpacct.framework.core.utils.LogUtils;
import com.simpacct.framework.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The entrance of data service.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Controller
public class RequestServiceEntranceController{

    @Autowired
    public RestConfig restConfig;

    private static final String[] STILL_IN_WORK = new String[]{"This Interface Is Still In Developing."};
    private static final Map<String, ModelFactory> DISPATCHER_MAPPING = new HashMap<>();
    private static final Map<String, List<Class>> URL_MAPPING_METHOD = new HashMap<>();
    private static PageNotFoundException pageNotFoundException;
    private List<WorkStep> workSteps;
    private List<WorkStep> beforeResponseSteps;

    Object requestDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestPath = request.getRequestURI().replace(RequestPath.System.SystemName, "");
        if (DISPATCHER_MAPPING.containsKey(requestPath)){
            ModelFactory modelFactory = DISPATCHER_MAPPING.get(requestPath);
            LogUtils.info("Start process request: " + requestPath);
            final HttpEntity[] entity = {new HttpEntity(request, response, modelFactory.createModel(), URL_MAPPING_METHOD.get(requestPath))};
            workSteps.forEach((step) -> entity[0] = step.executeStep(entity[0]));
            final ResponseObj[] responseObj = {new ResponseObj(entity[0].getRequestModel(), modelFactory.getService().doProcess(entity[0]))};
            if (responseObj[0].getResponseObject() == null){
                return STILL_IN_WORK;
            } else {
                beforeResponseSteps.forEach((step) -> responseObj[0] = step.executeStep(responseObj[0]));
                return responseObj[0].getResponseObject();
            }
        }
        if (pageNotFoundException == null){
            pageNotFoundException = PageNotFoundException.getException();
        }
        throw pageNotFoundException;
    }

    @PostConstruct
    private void initEnvironments(){
        restConfig.initAndGetMapping().forEach((rest) -> rest.getURLMapping().forEach((k, v) -> {
            if (RequestPath.isOpenInterface(k)) {
                DISPATCHER_MAPPING.put(k, rest);
                URL_MAPPING_METHOD.put(k, v);
            }
        }));
    }

    public void setWorkSteps(List<WorkStep> workSteps) {
        this.workSteps = workSteps;
    }

    public void setBeforeResponseSteps(List<WorkStep> beforeResponseSteps) {
        this.beforeResponseSteps = beforeResponseSteps;
    }
}
