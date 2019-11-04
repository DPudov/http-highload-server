package com.dpudov.server.response;

import java.util.Map;

public class ResponseCodes {
    public static final int STATUS_OK = 200;

    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_DEFAULT = 405;

    public static Map<Integer, String> statuses;

    static {
        statuses = Map.of(STATUS_OK, "OK",
                STATUS_FORBIDDEN, "Forbidden",
                STATUS_NOT_FOUND, "Not Found",
                STATUS_METHOD_NOT_ALLOWED, "Method not allowed");
    }
}
