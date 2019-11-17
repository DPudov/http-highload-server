package com.dpudov.server.internals;

import java.util.concurrent.LinkedBlockingQueue;

class TaskExecutor implements Runnable {
    private final LinkedBlockingQueue<Runnable> queue;
    private final Thread newThread;

    public TaskExecutor(LinkedBlockingQueue<Runnable> queue) {
        this.queue = queue;
        newThread = new Thread(this);
        newThread.start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            Runnable task = null;
            if (!queue.isEmpty()) {
                task = queue.remove();
            }

            if (task != null) {
                task.run();
            }
        }
    }

    public synchronized void stop() {
        newThread.interrupt();
    }
}
