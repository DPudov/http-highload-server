package com.dpudov.server;

import com.dpudov.server.internals.ServerConfig;

import java.io.IOException;

public class Main {
    private static final String DEFAULT_CONFIG_FILE_NAME = "./etc/httpd.conf";
    private static final String DEFAULT_PORT = "80";

    public static void main(String[] args) {
        try {
            String configFile = DEFAULT_CONFIG_FILE_NAME;
            String port = DEFAULT_PORT;
            if (args.length > 1) {
                configFile = args[1];
            }

            if (args.length > 2) {
                port = args[2];
            }
            System.out.println("Loading configs from file: " + configFile);
            ServerConfig serverConfig = ServerConfig.loadFromFile(configFile);
            Server server = new Server(Integer.parseInt(port), serverConfig);
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            System.out.println("Running server at localhost:" + port);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
