package com.easyrest.framework.core.services.job.impl;

import com.easyrest.framework.core.model.job.GetJobResultModel;
import com.easyrest.framework.core.model.request.HttpEntity;
import com.easyrest.framework.core.services.business.api.RequestProcessService;
import com.easyrest.framework.core.services.job.api.JobResourceStaticService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Service
public class GetJobResultServiceImpl implements RequestProcessService {
    @Override
    public Object doProcess(HttpEntity httpEntity) {
        GetJobResultModel getJobResultModel = (GetJobResultModel) httpEntity.getRequestModel();
        return JobResourceStaticService.getJobResourceStaticService().getJobResult(getJobResultModel.getJobId());
    }
}
