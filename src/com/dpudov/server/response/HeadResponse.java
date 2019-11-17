package com.dpudov.server.response;

import com.dpudov.server.util.Headers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static com.dpudov.server.response.ResponseConstants.NEW_LINE;

public class HeadResponse implements Writable {
    private final int statusCode;
    private final File sendingFile;
    private final HashMap<String, String> headers;


    public HeadResponse(int statusCode, File file) {
        this.headers = new HashMap<>();
        this.statusCode = statusCode;
        this.sendingFile = file;
        setContentLength();
        try {
            setContentType();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write(getResponseLine());
        writer.write(NEW_LINE);
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

    private String getResponseLine() {
        return ResponseConstants.PROTOCOL
                + " "
                + statusCode
                + " "
                + ResponseCodes.statuses.getOrDefault(statusCode, "Method not allowed");
    }

    private void setContentType() throws IOException {
        if (sendingFile == null) {
            return;
        }
        Path source = Paths.get(sendingFile.toURI());
        String contentType = Files.probeContentType(source);
        if (contentType != null) {
            headers.put(Headers.CONTENT_TYPE, contentType);
        }
    }

    private void setContentLength() {
        headers.put(Headers.CONTENT_LENGTH, String.valueOf(sendingFile.length()));
    }

}
