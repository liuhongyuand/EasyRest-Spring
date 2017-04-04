package com.easyrest.framework.core.services.job.impl;

import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.services.generateId.api.GenerateIdStaticService;
import com.easyrest.framework.core.services.job.Job;
import com.easyrest.framework.core.services.job.JobStatus;
import com.easyrest.framework.core.services.job.api.JobResourceService;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
@Service
public class JobResourceServiceImpl implements JobResourceService {

    @Override
    public String getJobId() {
        return GenerateIdStaticService.createId();
    }

    @Override
    public void submitJob(Job job) {
        JobPool.add(job);
    }

    @Override
    public Job getJob(String jobId) {
        return JobPool.getJob(jobId);
    }

    @Override
    public JobStatus getJobStatus(String jobId) {
        return JobPool.getJobStatus(jobId);
    }

    @Override
    public ResponseEntity<?> getJobResult(String jobId) {
        return JobPool.getJob(jobId).getResponseEntity();
    }
}
