package com.dpudov.server.response;

import java.io.*;
import java.util.HashMap;

public class HeadResponse implements Writable {
    private static final String NEW_LINE = "\r\n";
    private static final int BUFFER_SIZE = 4096;
    private String protocol = "HTTP/1.1";
    private int statusCode;
    private File sendingFile;
    private HashMap<String, String> headers;


    public HeadResponse(int statusCode, File file) {
        this.headers = new HashMap<>();
        this.statusCode = statusCode;
        this.sendingFile = file;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(getResponseLine());
        writer.write(NEW_LINE);
        for (String key : headers.keySet()) {
            writer.write(key + ":" + headers.get(key));
            writer.write(NEW_LINE);
        }

        writer.flush();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getResponseLine() {
        return protocol
                + " "
                + statusCode
                + " "
                + ResponseCodes.statuses.getOrDefault(statusCode, "Method not allowed");
    }
}
