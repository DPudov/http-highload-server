package com.dpudov.server.internals;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final LinkedBlockingQueue<Runnable> queue;
    private final List<TaskExecutor> executors = new LinkedList<>();
    private boolean isRunning = true;
    private final DocumentRootContainer documentRoot;

    public ThreadPool(ServerConfig config) {
        int threadLimit = config.getThreadLimit();
        int cpuLimit = config.getCpuLimit();
        documentRoot = new DocumentRootContainer(config.getDocumentRoot());
        queue = new LinkedBlockingQueue<>(threadLimit * cpuLimit * 8);
        TaskExecutor executor = new TaskExecutor(queue);
        for (int i = 0; i < threadLimit; i++) {
            executors.add(executor);
        }
    }

    private synchronized void addWorker(Runnable runnable) {
        if (isRunning) {
            queue.add(runnable);
//            queue.notify();
        }
    }

    public synchronized void stop() {
        isRunning = false;
        for (TaskExecutor executor : executors) {
            executor.stop();
        }
    }

    public void addWorker(Socket client) {
        addWorker(new Worker(client, documentRoot));
    }
}
