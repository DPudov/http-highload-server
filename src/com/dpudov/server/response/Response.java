package com.dpudov.server.response;

import java.io.OutputStream;

public class Response {
    private OutputStream to;
    private int code;
    private String contentType;
    private int contentLength;

    public void send() {

    }

    public static Builder newBuilder() {
        return new Response().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setOutputStream(OutputStream stream) {
            Response.this.to = stream;
            return this;
        }

        public Response build() {
            return Response.this;
        }
    }
}
