package com.easyrest.framework.core.model.job;

import com.easyrest.framework.core.annotations.method.Get;
import com.easyrest.framework.core.annotations.parameter.AllDefined;
import com.easyrest.framework.core.model.request.AbstractRequestModel;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Get("/getJobResult")
@AllDefined
public class GetJobResultModel extends AbstractRequestModel{

    private String jobId;

    public String getJobId() {
        return jobId;
    }
}
