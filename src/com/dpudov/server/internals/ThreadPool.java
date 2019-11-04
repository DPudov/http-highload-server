package com.dpudov.server.internals;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private LinkedBlockingQueue<Runnable> queue;
    private List<TaskExecutor> executors = new LinkedList<>();
    private volatile boolean isRunning = true;

    public ThreadPool(ServerConfig config) {
        int threadLimit = config.getThreadLimit();
        int cpuLimit = config.getCpuLimit();
        queue = new LinkedBlockingQueue<>(threadLimit * cpuLimit * 8);
        TaskExecutor executor = new TaskExecutor(queue);
        for (int i = 0; i < threadLimit; i++) {
            executors.add(executor);
        }
    }

    public void addRunnable(Runnable runnable) {
        if (isRunning)
            queue.add(runnable);
    }

    public void stop() {
        isRunning = false;
        for (TaskExecutor executor : executors) {
            executor.stop();
        }
    }
}
