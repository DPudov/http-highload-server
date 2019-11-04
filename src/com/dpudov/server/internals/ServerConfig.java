package com.dpudov.server.internals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {
    public static final String CPU_LIMIT_KEY = "cpu_limit";
    public static final String THREAD_LIMIT_KEY = "thread_limit";
    public static final String DOCUMENT_ROOT_KEY = "document_root";

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
        builder.setCpuLimit(Integer.parseInt(config.getProperty(CPU_LIMIT_KEY)));
        builder.setThreadLimit(Integer.parseInt(config.getProperty(THREAD_LIMIT_KEY)));
        builder.setDocumentRoot(config.getProperty(DOCUMENT_ROOT_KEY));
        return builder.build();
    }

    public static Builder newBuilder() {
        return new ServerConfig().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setCpuLimit(int cpuLimit) {
            ServerConfig.this.cpuLimit = cpuLimit;
            return this;
        }

        public Builder setDocumentRoot(String documentRoot) {
            ServerConfig.this.documentRoot = documentRoot;
            return this;
        }

        public Builder setThreadLimit(int threadLimit) {
            ServerConfig.this.threadLimit = threadLimit;
            return this;
        }

        public ServerConfig build() {
            return ServerConfig.this;
        }
    }
}
