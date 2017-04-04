package com.easyrest.framework.core.services.scheduler.api;

/**
 * Created by ${Lhy} on 17/2/16.
 */
public interface SchedulerService {

    void bindScheduler(long startTime, long gap, SchedulerProcessor schedulerProcessor, boolean needInit);

}
