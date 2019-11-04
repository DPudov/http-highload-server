package com.dpudov.server.internals;

public class ThreadPool {
    ConcurrentLinkedQueue<Runnable> queue;

    public ThreadPool(ServerConfig config) {
        queue = new ConcurrentLinkedQueue<>();

    }

    public void addRunnable(Runnable runnable) {
        queue.add(runnable);
    }
}
