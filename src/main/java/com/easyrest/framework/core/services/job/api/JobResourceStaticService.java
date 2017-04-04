package com.easyrest.framework.core.services.job.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Component
public class JobResourceStaticService {

    @Autowired
    private JobResourceService jobResourceService;

    private static JobResourceService jobResourceStaticService;

    public static JobResourceService getJobResourceStaticService() {
        return jobResourceStaticService;
    }

    @PostConstruct
    private void setJobResourceStaticService() {
        JobResourceStaticService.jobResourceStaticService = jobResourceService;
    }
}
