package com.vk.vkphoto.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VkThreadPool {

    private final ThreadPoolExecutor executor;

    public VkThreadPool(Integer count, LinkedBlockingDeque<Runnable> queue) {
        executor = new ThreadPoolExecutor(count, count, 30, TimeUnit.SECONDS, queue);
    }

    public void post(Runnable runnable) {
        executor.execute(runnable);
    }
}