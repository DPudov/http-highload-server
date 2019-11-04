package com.dpudov.server;

import com.dpudov.server.internals.ServerConfig;
import com.dpudov.server.internals.ServerState;
import com.dpudov.server.internals.ThreadPool;
import com.dpudov.server.internals.Worker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    public static final String SERVER_NAME = "dpudov-highload-java-server";
    private ServerSocket socket;
    private ServerState state;
    private ServerConfig config;
    private ThreadPool pool;


    public Server(int port, ServerConfig config) throws IOException {
        this.socket = new ServerSocket(port);
        this.state = new ServerState();
        this.config = config;
        this.pool = new ThreadPool(config);
    }

    @Override
    public void run() {
        state.on();
        while (state.isRunning()) {
            try (Socket client = socket.accept()) {
                pool.addRunnable(new Worker(client, config.getDocumentRoot()));
            } catch (IOException e) {
                if (state.isRunning()) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        state.off();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
