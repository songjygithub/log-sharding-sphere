package com.songjy.log.sharding.sphere.commons;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author songjy
 * @date 2020-10-16
 */
public class AsyncManager {
    /**
     * 线程池
     */
    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(250,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    /**
     * 单例
     */
    private static AsyncManager instance = new AsyncManager();

    private AsyncManager() {
    }

    public static AsyncManager getInstance() {
        return instance;
    }


    /**
     * @param runnable 线程
     */
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

}
