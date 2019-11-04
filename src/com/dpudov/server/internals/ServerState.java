package com.dpudov.server.internals;

public class ServerState {
    public static final boolean SERVER_ON = true;
    public static final boolean SERVER_OFF = false;
    private boolean isRunning;

    public ServerState() {
        this.isRunning = false;
    }

    public void on() {
        isRunning = true;
    }

    public void off() {
        isRunning = false;
    }

    public void toggleRunning() {
        isRunning = !isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
