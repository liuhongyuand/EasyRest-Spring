package com.easyrest.framework.core.services.job.impl;

import com.easyrest.framework.core.services.job.Job;
import com.easyrest.framework.core.services.job.JobStatus;
import com.easyrest.framework.core.services.scheduler.api.SchedulerProcessor;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.exception.ConditionMissingException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by liuhongyu.louie on 2017/4/4.
 */
public class JobPool implements SchedulerProcessor {

    private static final int WAIT_TIME = 1000;

    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

    private static final Map<String, Job> jobPool = new HashMap<>();

    static void add(Job job){
        try {
            if (LOCK.writeLock().tryLock(WAIT_TIME, TimeUnit.MILLISECONDS)) {
                jobPool.put(job.getJobId(), job);
            } else {
                throw new ConditionMissingException("The job submit failed");
            }
        } catch (InterruptedException e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    static JobStatus getJobStatus(String jobId){
        if (jobPool.containsKey(jobId) && !jobPool.get(jobId).isExpired()){
            return jobPool.get(jobId).getJobStatus();
        } else {
            throw new ConditionMissingException("The job is not exists or expired");
        }
    }

    static Job getJob(String jobId){
        if (jobPool.containsKey(jobId) && !jobPool.get(jobId).isExpired()){
            return jobPool.get(jobId);
        } else {
            throw new ConditionMissingException("The job is not exists or expired");
        }
    }

    @Override
    public void execute() {
        List<String> expiredJobList = new LinkedList<>();
        try {
            if (LOCK.writeLock().tryLock(WAIT_TIME, TimeUnit.MILLISECONDS)) {
                jobPool.forEach((k, v) -> {
                    if (v.isExpired()){
                        expiredJobList.add(k);
                    }
                });
                expiredJobList.forEach(jobPool::remove);
            }
        } catch (InterruptedException e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            LOCK.writeLock().unlock();
        }
    }
}
