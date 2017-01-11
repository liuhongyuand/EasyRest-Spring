package com.simpacct.framework.core.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuhongyu.louie on 2016/10/1.
 */
public class ThreadPoolResources {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 10);

}
