package com.dpudov.server.internals;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskExecutor implements Runnable {
    private final LinkedBlockingQueue<Runnable> queue;
    private Thread newThread;
    private volatile boolean isRunning = true;

    public TaskExecutor(LinkedBlockingQueue<Runnable> queue) {
        this.queue = queue;
        newThread = new Thread(this);
        newThread.start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            Runnable task = null;
            synchronized (queue) {
                if (!queue.isEmpty())
                    task = queue.remove();
            }
            if (task != null) {
                task.run();
            }
        }
    }

    public void stop() {
        isRunning = false;
        newThread.interrupt();
    }
}
