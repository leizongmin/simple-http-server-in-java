package com.leizm.simplehttpserver;

public class HttpError extends Error {
    private int statusCode;
    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.errorMessage = message;
    }

    public static HttpError badRequest() {
        return new HttpError(400, "Bad Request");
    }
}
