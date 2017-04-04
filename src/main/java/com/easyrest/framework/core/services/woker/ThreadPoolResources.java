package com.easyrest.framework.core.services.woker;

import com.easyrest.framework.core.services.job.Job;
import com.easyrest.framework.core.services.job.JobStatus;
import com.easyrest.framework.core.services.job.api.JobResourceStaticService;
import com.easyrest.framework.core.utils.LogUtils;
import com.easyrest.framework.exception.ConditionMissingException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * Created by liuhongyu.louie on 2016/10/1.
 */
@Component
public class ThreadPoolResources {

    private static boolean systemUp = true;

    public static final ScheduledExecutorService SCHEDULER_EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    private static final ExecutorService WORKER_EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);

    private static final BlockingQueue<Job> WORKER_POOL = new LinkedBlockingDeque<>();

    public static String submitWorker(Worker worker) {
        if (WORKER_POOL.remainingCapacity() > 0) {
            String jobId = JobResourceStaticService.getJobResourceStaticService().getJobId();
            JobResourceStaticService.getJobResourceStaticService().submitJob(new Job(jobId, worker));
            WORKER_POOL.add(JobResourceStaticService.getJobResourceStaticService().getJob(jobId));
            return jobId;
        } else {
            throw new ConditionMissingException("The element cannot be added at this time due to capacity restrictions");
        }
    }

    public static void shutdownWorkers(){
        try {
            systemUp = false;
            WORKER_EXECUTOR.shutdown();
            WORKER_POOL.clear();
        } catch (Exception ignored){}
    }

    @PostConstruct
    private void queueExecutor(){
        WORKER_EXECUTOR.execute(() -> {
            while (systemUp) {
                try {
                    Job job = WORKER_POOL.take();
                    if (job != null && job.getWorker() != null) {
                        job.setJobStatus(JobStatus.RUNNING);
                        WORKER_EXECUTOR.execute(job.getWorker());
                    }
                } catch (InterruptedException e) {
                    LogUtils.error(e.getMessage(), e);
                }
            }
        });
    }

}
