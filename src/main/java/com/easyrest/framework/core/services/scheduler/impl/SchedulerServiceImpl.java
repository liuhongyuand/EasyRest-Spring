package com.easyrest.framework.core.services.scheduler.impl;

import com.easyrest.framework.core.services.scheduler.api.SchedulerProcessor;
import com.easyrest.framework.core.services.scheduler.api.SchedulerService;
import com.easyrest.framework.core.services.woker.ThreadPoolResources;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by ${Lhy} on 17/2/16.
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Override
    public void bindScheduler(long startTime, long gap, SchedulerProcessor schedulerProcessor, boolean needInit) {
        if (needInit){
            schedulerProcessor.execute();
        }
        ThreadPoolResources.SCHEDULER_EXECUTOR.scheduleAtFixedRate(schedulerProcessor::execute, startTime, gap, TimeUnit.MILLISECONDS);
    }

}
