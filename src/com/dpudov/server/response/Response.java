package com.dpudov.server.response;

import com.dpudov.server.internals.Cache;
import com.dpudov.server.util.FilenameUtils;
import com.dpudov.server.util.Headers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

import static com.dpudov.server.response.ResponseConstants.NEW_LINE;

public class Response implements Writable {
    private static final int BUFFER_SIZE = 4 * 4096;
    private final int statusCode;
    private final File sendingFile;
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
            char[] fromCache = Cache.serveFromCache(sendingFile.getAbsolutePath());
            if (fromCache != null) {
                writer.write(fromCache, 0, fromCache.length);
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sendingFile)));
                CharArrayWriter cacheWriter = new CharArrayWriter(BUFFER_SIZE);
                char[] buffer = new char[BUFFER_SIZE];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, read);
                    cacheWriter.write(buffer, 0, read);
                }
                Cache.writeToCache(sendingFile.getAbsolutePath(), cacheWriter.toCharArray());
                cacheWriter.close();
                reader.close();
            }
        }

        writer.flush();
    }

    private String getResponseLine() {
        return ResponseConstants.PROTOCOL
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
            headers.put(Headers.CONTENT_TYPE, contentType);
        } else {
            Optional<String> ext = FilenameUtils.getExtensionByStringHandling(source.toString());
            if (ext.isPresent()) {
                String extension = ext.get();
                switch (extension) {
                    case "swf":
                        headers.put(Headers.CONTENT_TYPE, "application/x-shockwave-flash");
                        break;
                    case "js":
                        headers.put(Headers.CONTENT_TYPE, "text/javascript");
                        break;
                    case "css":
                        headers.put(Headers.CONTENT_TYPE, "text/css");
                        break;
                }
            }
        }
    }

    private void setContentLength() {
        headers.put(Headers.CONTENT_LENGTH, String.valueOf(sendingFile.length()));
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
