package com.easyrest.framework.core.services.woker;

import com.easyrest.framework.core.utils.LogUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by liuhongyu.louie on 2016/10/1.
 */
@Service
public class ThreadPoolResources {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);

    private static final BlockingQueue<Worker> workerQueue = new LinkedBlockingDeque<>();

    public static BlockingQueue<Worker> getWorkerQueue() {
        return workerQueue;
    }

    @PostConstruct
    private void queueExecutor(){
        EXECUTOR.execute(() -> {
            try {
                Worker worker = workerQueue.take();
                if (worker != null){
                    EXECUTOR.execute(worker);
                }
            } catch (InterruptedException e) {
                LogUtils.error(e.getMessage(), e);
            }
        });
    }

}
