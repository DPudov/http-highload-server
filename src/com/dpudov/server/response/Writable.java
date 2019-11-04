package com.dpudov.server.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public interface Writable {
    void send(OutputStream outputStream) throws IOException;

    HashMap<String, String> getHeaders();
}