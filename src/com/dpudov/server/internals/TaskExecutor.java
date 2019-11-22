package com.dpudov.server.internals;

import java.util.concurrent.BlockingQueue;

class TaskExecutor implements Runnable {
    private final BlockingQueue<Runnable> queue;
    private final Thread newThread;

    public TaskExecutor(BlockingQueue<Runnable> queue) {
        this.queue = queue;
        newThread = new Thread(this);
        newThread.start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = queue.take();
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void stop() {
        newThread.interrupt();
    }
}
