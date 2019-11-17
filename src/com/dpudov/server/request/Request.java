package com.dpudov.server.request;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;

public class Request {
    private InputStream inputStream;

    private String requestLine;

    private String method;
    private String uri;
    private String version;

    private HashMap<String, String> headers;

    private HashMap<String, String> params;

    private String path;

    private String query;

    private boolean keepAlive = false;

    private Request() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    public static Request parse(InputStream inputStream) throws Exception {
        Request request = new Request();
        request.inputStream = inputStream;

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        request.requestLine = reader.readLine();
        if (request.requestLine == null) {
            throw new Exception("connection closed");
        }

        String[] requestLineParts = request.requestLine.split(" ", 3);
        if (requestLineParts.length != 0)
            request.method = requestLineParts[0];
        if (requestLineParts.length > 1)
            request.uri = requestLineParts[1];
        if (requestLineParts.length > 2)
            request.version = requestLineParts[2];
//        System.out.println(Arrays.toString(requestLineParts));

        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            String[] lineParts = line.split(":", 2);
            if (lineParts.length == 2) {
                request.headers.put(lineParts[0], lineParts[1]);
            }
            line = reader.readLine();
        }

        if (request.uri != null) {
            String[] uriParts = request.uri.split("\\?", 2);
            if (uriParts.length == 2) {
                request.path = uriParts[0];
                request.query = uriParts[1];

                String[] keyValuePairs = request.query.split("&");
                for (String keyValuePair : keyValuePairs) {
                    String[] keyValue = keyValuePair.split("=", 2);
                    if (keyValue.length == 2) {
                        request.params.put(keyValue[0], keyValue[1]);
                    }
                }
            } else {
                request.path = request.uri;
                request.query = "";
            }
        }


        if (request.headers.getOrDefault("Connection", "close").equalsIgnoreCase("keep-alive")) {
            request.keepAlive = true;
        }

        return request;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getPath() {
        return URLDecoder.decode(path);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
}
