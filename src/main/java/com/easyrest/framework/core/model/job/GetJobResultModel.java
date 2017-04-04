package com.easyrest.framework.core.model.job;

import com.easyrest.framework.core.annotations.bean.BindService;
import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.parameter.AllDefined;
import com.easyrest.framework.core.model.request.AbstractRequestModel;
import com.easyrest.framework.core.services.job.impl.GetJobResultServiceImpl;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Get("/getJobResult")
@AllDefined
@BindService(GetJobResultServiceImpl.class)
public class GetJobResultModel extends AbstractRequestModel{

    private String jobId;

    public String getJobId() {
        return jobId;
    }
}
