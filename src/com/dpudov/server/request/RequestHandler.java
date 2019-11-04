package com.dpudov.server.request;

import com.dpudov.server.response.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestHandler {
    private String documentRoot;

    public RequestHandler(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public Writable handle(Request request) {
        String path = request.getPath();
        Writable response;
        switch (request.getMethod()) {
            case Method.GET:
                Path requestedFile = Paths.get(documentRoot, path);
                if (requestedFile.normalize().startsWith(Paths.get(documentRoot).normalize())) {
                    if (Files.exists(requestedFile)) {
                        if (!Files.isDirectory(requestedFile)) {
                            response = new Response(ResponseCodes.STATUS_OK,
                                    new File(Paths.get(documentRoot, path).toString()));
                        } else {
                            response = new EmptyResponse(ResponseCodes.STATUS_FORBIDDEN);
                        }
                    } else {
                        response = new EmptyResponse(ResponseCodes.STATUS_NOT_FOUND);
                    }
                } else {
                    response = new EmptyResponse(ResponseCodes.STATUS_FORBIDDEN);
                }
                break;
            case Method.HEAD:
                if (Files.exists(Paths.get(documentRoot, path))) {
                    response = new HeadResponse(ResponseCodes.STATUS_OK,
                            new File(Paths.get(documentRoot, path).toString()));
                } else {
                    response = new EmptyResponse(ResponseCodes.STATUS_NOT_FOUND);
                }
                break;
            default:
                response = new EmptyResponse(ResponseCodes.STATUS_METHOD_NOT_ALLOWED);
                break;
        }
        return response;
    }
}
