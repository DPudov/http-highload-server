package com.dpudov.server.internals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {
    private static final String CPU_LIMIT_KEY = "cpu_limit";
    private static final String THREAD_LIMIT_KEY = "thread_limit";
    private static final String DOCUMENT_ROOT_KEY = "document_root";
    private static final String DEFAULT_CPU_LIMIT = "4";
    private static final String DEFAULT_THREAD_LIMIT = "256";
    private static final String DEFAULT_DOCUMENT_ROOT = "/var/www/html";
    private int cpuLimit;
    private int threadLimit;
    private String documentRoot;

    public ServerConfig() {

    }

    public ServerConfig(int cpuLimit, int threadLimit, String documentRoot) {
        this.cpuLimit = cpuLimit;
        this.threadLimit = threadLimit;
        this.documentRoot = documentRoot;
    }

    public int getThreadLimit() {
        return threadLimit;
    }

    public int getCpuLimit() {
        return cpuLimit;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public static ServerConfig loadFromFile(String filename) throws IOException {
        Properties config = new Properties();
        config.load(new FileInputStream(filename));
        ServerConfig.Builder builder = newBuilder();
        builder.setCpuLimit(Integer.parseInt(config.getProperty(CPU_LIMIT_KEY, DEFAULT_CPU_LIMIT)));
        builder.setThreadLimit(Integer.parseInt(config.getProperty(THREAD_LIMIT_KEY, DEFAULT_THREAD_LIMIT)));
        builder.setDocumentRoot(config.getProperty(DOCUMENT_ROOT_KEY, DEFAULT_DOCUMENT_ROOT));
        return builder.build();
    }

    private static Builder newBuilder() {
        return new ServerConfig().new Builder();
    }

    class Builder {
        private Builder() {

        }

        void setCpuLimit(int cpuLimit) {
            ServerConfig.this.cpuLimit = cpuLimit;
        }

        void setDocumentRoot(String documentRoot) {
            ServerConfig.this.documentRoot = documentRoot;
        }

        void setThreadLimit(int threadLimit) {
            ServerConfig.this.threadLimit = threadLimit;
        }

        ServerConfig build() {
            return ServerConfig.this;
        }
    }
}
