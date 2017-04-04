package com.easyrest.framework.core.services.job.api;

import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.services.job.Job;
import com.easyrest.framework.core.services.job.JobStatus;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
public interface JobResourceService {

    String getJobId();

    void submitJob(Job job);

    Job getJob(String jobId);

    JobStatus getJobStatus(String jobId);

    ResponseEntity<?> getJobResult(String jobId);

}
