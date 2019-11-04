package com.dpudov.server;

import com.dpudov.server.internals.ServerConfig;

import java.io.IOException;

public class Main {
    public static final String DEFAULT_CONFIG_FILE_NAME = "/etc/httpd.conf";
    public static final int DEFAULT_PORT = 80;

    public static void main(String[] args) {
        try {
            ServerConfig serverConfig = ServerConfig.loadFromFile(DEFAULT_CONFIG_FILE_NAME);
            Server server = new Server(DEFAULT_PORT, serverConfig);
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
