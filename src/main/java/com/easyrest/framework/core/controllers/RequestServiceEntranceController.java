package com.easyrest.framework.core.controllers;

import com.easyrest.framework.configuration.RequestPath;
import com.easyrest.framework.configuration.SystemRestConfig;
import com.easyrest.framework.core.annotations.async.AsyncRequest;
import com.easyrest.framework.core.model.ModelFactory;
import com.easyrest.framework.core.model.TransactionContext;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.model.response.ResponseObj;
import com.easyrest.framework.core.services.job.impl.JobPool;
import com.easyrest.framework.core.services.scheduler.api.SchedulerProcessor;
import com.easyrest.framework.core.services.scheduler.api.SchedulerService;
import com.easyrest.framework.core.services.woker.ThreadPoolResources;
import com.easyrest.framework.core.services.woker.Worker;
import com.easyrest.framework.core.services.workStep.api.AfterServiceStep;
import com.easyrest.framework.core.services.workStep.api.BeforeServiceStep;
import com.easyrest.framework.core.utils.LogUtils;
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

/**
 * The entrance of data service.
 * Created by liuhongyu.louie on 2016/9/29.
 */
@Controller
public class RequestServiceEntranceController{

    private final SystemStartupService systemStartupService;

    private final SystemRestConfig restConfig;

    private static final Map<String, ModelFactory> DISPATCHER_MAPPING = new HashMap<>();
    private static final Map<String, List<Class>> URL_MAPPING_METHOD = new HashMap<>();
    private static List<BeforeServiceStep> beforeServiceSteps;
    private static List<AfterServiceStep> afterServiceSteps;
    private static final SchedulerProcessor PROCESSOR = new JobPool();
    private final SchedulerService schedulerService;

    @Autowired
    public RequestServiceEntranceController(SystemStartupService systemStartupService, SystemRestConfig restConfig, SchedulerService schedulerService) {
        this.systemStartupService = systemStartupService;
        this.restConfig = restConfig;
        this.schedulerService = schedulerService;
    }

    ResponseObj requestDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestPath = request.getRequestURI().replace(RequestPath.System.SystemName, "");
        if (DISPATCHER_MAPPING.containsKey(requestPath)){
            ModelFactory modelFactory = DISPATCHER_MAPPING.get(requestPath);
            LogUtils.info("Start process request: " + requestPath, this.getClass());
            final HttpEntity[] entity = {new HttpEntity(request, response, modelFactory.createModel(), URL_MAPPING_METHOD.get(requestPath))};
            try {
                beforeServiceSteps.forEach((step) -> entity[0] = step.executeStep(entity[0]));
                final ResponseObj[] responseObj = getResponse(entity[0], modelFactory);
                if (responseObj[0].getResponseObject() != null) {
                    afterServiceSteps.forEach((step) -> responseObj[0] = step.executeStep(responseObj[0]));
                    return responseObj[0];
                } else {
                    throw new PageNotFoundException("The result is null");
                }
            } catch (Exception e){
                TransactionContext transactionContexts = entity[0].getTransactionContext();
                if (transactionContexts != null && transactionContexts.isTransactionStart() && !transactionContexts.getTransactionStatus().isCompleted()){
                    transactionContexts.getTransactionRollback().apply(transactionContexts.getTransactionStatus());
                    LogUtils.error(e.getMessage(), e);
                }
                throw e;
            }
        }
        throw PageNotFoundException.getException("The resource not found");
    }

    private ResponseObj[] getResponse(HttpEntity entity, ModelFactory modelFactory){
        if (entity.getRequestModel().getClass().isAnnotationPresent(AsyncRequest.class)) {
            return new ResponseObj[]{new ResponseObj(entity, ResponseEntity.buildOkResponse(ThreadPoolResources.submitWorker(new Worker(entity, modelFactory))))};
        } else {
            return new ResponseObj[]{new ResponseObj(entity, modelFactory.getService().doProcess(entity))};
        }
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
        schedulerService.bindScheduler(0, 1000 * 60 * 10, PROCESSOR, false);
    }

    public static void setBeforeServiceSteps(List<BeforeServiceStep> _beforeServiceSteps) {
        beforeServiceSteps = _beforeServiceSteps;
    }

    public static void setAfterServiceSteps(List<AfterServiceStep> _afterServiceSteps) {
        afterServiceSteps = _afterServiceSteps;
    }
}
