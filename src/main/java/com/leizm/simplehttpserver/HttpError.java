package com.leizm.simplehttpserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpError extends Error {
    private int statusCode;
    private String errorMessage;

    public static HttpError badRequest() {
        return new HttpError(400, "Bad Request");
    }
}
