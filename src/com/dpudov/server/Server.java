package com.dpudov.server;

import com.dpudov.server.internals.ServerConfig;
import com.dpudov.server.internals.ServerState;
import com.dpudov.server.internals.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final String SERVER_NAME = "dpudov-highload-java-server";
    private ServerSocket socket;
    private ServerState state;
    private ServerConfig config;
    private ThreadPool pool;
    private boolean isRunning = true;
    private int port;

    public Server(int port, ServerConfig config) {
        this.port = port;
        this.state = new ServerState();
        this.config = config;
        this.pool = new ThreadPool(config);
    }

    @Override
    public void run() {
//        state.on();
        openServerSocket();
        while (isRunning()) {
            Socket client = null;
            try {
                client = socket.accept();
            } catch (IOException e) {
                if (isRunning()) {
                    e.printStackTrace();
                }
            }
            pool.addWorker(client);
        }
    }


    private synchronized boolean isRunning() {
        return this.isRunning;
    }

    public synchronized void stop() {
//        state.off();
        isRunning = false;
        try {
            socket.close();
            pool.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openServerSocket() {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
