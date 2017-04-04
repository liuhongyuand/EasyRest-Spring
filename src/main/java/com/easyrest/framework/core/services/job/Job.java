package com.easyrest.framework.core.services.job;

import com.easyrest.framework.core.model.response.ResponseEntity;
import com.easyrest.framework.core.services.woker.Worker;

import java.util.Date;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
public class Job {

    private String jobId;

    private long creationTime;

    private long startTime;

    private long finishTime;

    private long waitAndRunningExpiredTime = 60 * 1000 * 1;

    //1 min default
    private long expiredTime = 60 * 1000 * 1;

    private JobStatus jobStatus = JobStatus.NOT_START;

    private Worker worker;

    private ResponseEntity responseEntity;

    public Job(String jobId, Worker worker){
        this.jobId = jobId;
        this.worker = worker;
        this.creationTime = new Date().getTime();
        this.worker.setResponseEntity(_responseEntity -> {
            finishTime = new Date().getTime();
            responseEntity = _responseEntity;
            jobStatus = JobStatus.FINISHED;
            return null;
        });
    }

    public String getJobId() {
        return jobId;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isExpired() {
        long currentTime = new Date().getTime();
        return jobStatus.equals(JobStatus.NOT_START) && currentTime - creationTime >= waitAndRunningExpiredTime || jobStatus.equals(JobStatus.RUNNING) && currentTime - startTime >= waitAndRunningExpiredTime || jobStatus.equals(JobStatus.FINISHED) && currentTime - finishTime >= expiredTime;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
        if (jobStatus.equals(JobStatus.RUNNING)){
            startTime = new Date().getTime();
        }
    }

    public Worker getWorker() {
        return worker;
    }

    public ResponseEntity getResponseEntity() {
        if (isExpired()){
            return ResponseEntity.buildFailedResponse("The job is expired");
        } else {
            if (jobStatus.equals(JobStatus.NOT_START)) {
                return ResponseEntity.buildFailedResponse("The job has not start");
            } else if (jobStatus.equals(JobStatus.RUNNING)) {
                return ResponseEntity.buildFailedResponse("The job is still running");
            }
            return responseEntity;
        }
    }

}
