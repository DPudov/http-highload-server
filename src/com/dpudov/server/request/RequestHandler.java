package com.dpudov.server.request;

import java.io.InputStream;

public class RequestHandler {
    private InputStream from;

    public RequestHandler(InputStream from) {
        this.from = from;
    }
}
