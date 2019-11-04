package com.dpudov.server.internals;

import com.dpudov.server.Server;
import com.dpudov.server.request.Request;
import com.dpudov.server.request.RequestHandler;
import com.dpudov.server.response.Writable;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;

public class Worker implements Runnable {
    private Socket connection;
    private String documentRoot;

    public Worker(Socket connection, String documentRoot) {
        this.connection = connection;
        this.documentRoot = documentRoot;
    }

    @Override
    public void run() {
        try {
            InputStream requestStream = connection.getInputStream();
            OutputStream responseStream = connection.getOutputStream();
            RequestHandler requestProcessor = new RequestHandler(documentRoot);
            Request request = Request.parse(requestStream);

            Writable response = requestProcessor.handle(request);
            response.getHeaders().put("Server", Server.SERVER_NAME);
            response.getHeaders().put("Date", Calendar.getInstance().getTime().toString());
            response.getHeaders().put("Connection", "close");
            response.send(responseStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
