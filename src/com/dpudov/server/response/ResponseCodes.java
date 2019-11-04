package com.dpudov.server.response;

public class ResponseCodes {
    public static final int STATUS_OK = 200;

    public static final int STATUS_FORBIDDEN = 403;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_METHOD_NOT_ALLOWED = 405;
    public static final int STATUS_DEFAULT = 405;

    public static String getStatus(int statusCode) {
        switch (statusCode) {
            case STATUS_OK:
                return "OK";
            case STATUS_FORBIDDEN:
                return "Forbidden";
            case STATUS_NOT_FOUND:
                return "Not Found";
            case STATUS_METHOD_NOT_ALLOWED:
                return "Method not allowed";
            default:
                return "Method not allowed";
        }
    }
}
