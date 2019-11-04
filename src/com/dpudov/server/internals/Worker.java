package com.dpudov.server.internals;

import com.dpudov.server.request.RequestHandler;
import com.dpudov.server.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket connection;

    public Worker(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            InputStream requestStream = connection.getInputStream();
            OutputStream responseStream = connection.getOutputStream();
            RequestHandler requestProcessor = new RequestHandler(requestStream);
            Response response = Response.newBuilder()
                    .setOutputStream(responseStream)
                    .build();

            response.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
