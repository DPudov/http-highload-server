package com.dpudov.server.response;

import com.dpudov.server.util.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

public class Response implements Writable {
    private static final String NEW_LINE = "\r\n";
    private static final int BUFFER_SIZE = 4096;
    private String protocol = "HTTP/1.1";
    private int statusCode;
    private File sendingFile;
    private HashMap<String, String> headers;


    public Response(int statusCode, File file) {
        this.headers = new HashMap<>();
        this.statusCode = statusCode;
        this.sendingFile = file;

        try {
            setContentType();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentLength();
    }

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

        if (sendingFile != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sendingFile)));
            char[] buffer = new char[BUFFER_SIZE];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, read);
            }
            reader.close();
        }

        writer.flush();
    }

    public String getResponseLine() {
        return protocol
                + " "
                + statusCode
                + " "
                + ResponseCodes.statuses.getOrDefault(statusCode, "Method not allowed");
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    private void setContentType() throws IOException {
        if (sendingFile == null) {
            return;
        }
        Path source = Paths.get(sendingFile.toURI());
        String contentType = Files.probeContentType(source);
        if (contentType != null) {
            headers.put("Content-Type", contentType);
        } else {
            Optional<String> ext = FilenameUtils.getExtensionByStringHandling(source.toString());
            if (ext.isPresent()) {
                String extension = ext.get();
                switch (extension) {
                    case "swf":
                        headers.put("Content-Type", "application/x-shockwave-flash");
                        break;
                    case "js":
                        headers.put("Content-Type", "text/javascript");
                        break;
                    case "css":
                        headers.put("Content-Type", "text/css");
                        break;
                }
            }
        }
    }

    private void setContentLength() {
        headers.put("Content-Length", String.valueOf(sendingFile.length()));
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
