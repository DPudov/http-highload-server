package com.dpudov.server.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import static com.dpudov.server.response.ResponseConstants.NEW_LINE;

public class EmptyResponse implements Writable {
    private int statusCode;
    private HashMap<String, String> headers;

    public EmptyResponse(int statusCode) {
        this.headers = new HashMap<>();
        this.statusCode = statusCode;
    }

    @Override
    public void send(OutputStream outputStream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(getResponseLine());
        writer.write(NEW_LINE);
//        System.out.println("Empty response sending");
//        System.out.println(headers.toString());
        for (String key : headers.keySet()) {
            writer.write(key + ":" + headers.get(key));
            writer.write(NEW_LINE);
        }
        writer.write(NEW_LINE);
        writer.flush();
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getResponseLine() {

        return ResponseConstants.PROTOCOL + " " + statusCode + " " + ResponseCodes.statuses.getOrDefault(statusCode, "Method not allowed");
    }
}
