package com.dpudov.server.internals;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Runnable> queue;
    private final List<TaskExecutor> executors = new LinkedList<>();
    private boolean isRunning = true;
    private final DocumentRootContainer documentRoot;

    public ThreadPool(ServerConfig config) {
        int threadLimit = config.getThreadLimit();
        documentRoot = new DocumentRootContainer(config.getDocumentRoot());
        queue = new ArrayBlockingQueue<>(threadLimit + 1);
        TaskExecutor executor = new TaskExecutor(queue);
        for (int i = 0; i < threadLimit + 1; i++) {
            executors.add(executor);
        }
    }

    private void addWorker(Runnable runnable) {
        if (isRunning) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
