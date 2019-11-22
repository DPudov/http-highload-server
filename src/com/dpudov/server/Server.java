package com.dpudov.server;

import com.dpudov.server.internals.ServerConfig;
import com.dpudov.server.internals.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    public static final String SERVER_NAME = "dpudov-highload-java-server";
    private ServerSocket socket;
    private final List<ThreadPool> pools;
    private boolean isRunning = true;
    private final int port;
    private final int cpuLimit;

    public Server(int port, ServerConfig config) {
        this.port = port;
        this.cpuLimit = config.getCpuLimit();
        this.pools = new ArrayList<>(config.getCpuLimit());
        for (int i = 0; i < cpuLimit; i++) {
            pools.add(new ThreadPool(config));
        }
    }

    @Override
    public void run() {
        openServerSocket();
        int currentPool = 0;
        while (isRunning()) {
            Socket client = null;
            try {
                client = socket.accept();
                pools.get(currentPool).addWorker(client);
                currentPool = (currentPool + 1) % cpuLimit;
            } catch (IOException e) {
                if (isRunning()) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private synchronized boolean isRunning() {
        return this.isRunning;
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            socket.close();
            for (ThreadPool pool : pools) {
                pool.stop();
            }
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
