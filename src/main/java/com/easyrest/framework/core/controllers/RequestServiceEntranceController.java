package com.easyrest.framework.core.controllers;

import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.configuration.RestConfig;
import com.easyrest.framework.core.model.HttpEntity;
import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.model.TransactionContext;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.services.workStep.api.WorkStep;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.core.utils.ThreadPoolResources;
import com.easyrest.framework.easyrest.SystemStartupService;
import com.easyrest.framework.exception.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * The entrance of data service.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Controller
public class RequestServiceEntranceController{

    @Autowired
    private SystemStartupService systemStartupService;

    @Autowired
    public RestConfig restConfig;

    private static final long TIME_OUT = 1000 * 30L;
    private static final String[] STILL_IN_WORK = new String[]{"This Interface Is Still In Developing."};
    private static final Map<String, ModelFactory> DISPATCHER_MAPPING = new HashMap<>();
    private static final Map<String, List<Class>> URL_MAPPING_METHOD = new HashMap<>();
    private static PageNotFoundException pageNotFoundException;
    private List<WorkStep> workSteps;
    private List<WorkStep> beforeResponseSteps;

    ResponseObj requestDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestPath = request.getRequestURI().replace(RequestPath.System.SystemName, "");
        if (DISPATCHER_MAPPING.containsKey(requestPath)){
            Future<ResponseObj> responseObjFuture = null;
            ModelFactory modelFactory = DISPATCHER_MAPPING.get(requestPath);
            LogUtils.info("Start process request: " + requestPath, this.getClass());
            final HttpEntity[] entity = {new HttpEntity(request, response, modelFactory.createModel(), URL_MAPPING_METHOD.get(requestPath))};
            try {
                workSteps.forEach((step) -> entity[0] = step.executeStep(entity[0]));
                responseObjFuture = ThreadPoolResources.EXECUTOR_SERVICE.submit(() -> new ResponseObj(entity[0], modelFactory.getService().doProcess(entity[0])));
                //// TODO: 2017/1/17 lot of things to do{
                //      Many things that can do at the same time
                // }
                final ResponseObj[] responseObj = {responseObjFuture.get(TIME_OUT, TimeUnit.MILLISECONDS)};
                if (responseObj[0].getResponseObject() == null) {
                    return new ResponseObj(entity[0], STILL_IN_WORK);
                } else {
                    beforeResponseSteps.forEach((step) -> responseObj[0] = step.executeStep(responseObj[0]));
                    return responseObj[0];
                }
            } catch (Exception e){
                if (responseObjFuture != null && !responseObjFuture.isCancelled()){
                    responseObjFuture.cancel(true);
                }
                TransactionContext transactionContexts = entity[0].getTransactionContext();
                if (transactionContexts != null && transactionContexts.isTransactionStart() && !transactionContexts.getTransactionStatus().isCompleted()){
                    transactionContexts.getTransactionRollback().apply(transactionContexts.getTransactionStatus());
                    LogUtils.error(e.getMessage(), e);
                }
                throw e;
            }
        }
        if (pageNotFoundException == null){
            pageNotFoundException = PageNotFoundException.getException();
        }
        throw pageNotFoundException;
    }

    @PostConstruct
    private void initEnvironments(){
        systemStartupService.init();
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
