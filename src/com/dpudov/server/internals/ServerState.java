package com.dpudov.server.internals;

class ServerState {
    public static final boolean SERVER_ON = true;
    public static final boolean SERVER_OFF = false;
    private boolean isRunning;

    public ServerState() {
        this.isRunning = false;
    }

    public synchronized void on() {
        isRunning = true;
    }

    public synchronized void off() {
        isRunning = false;
    }

    public synchronized void toggleRunning() {
        isRunning = !isRunning;
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
}
