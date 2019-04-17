package com.leizm.simplehttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class RequestParser {
    protected InputStream input;
    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String version() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestParser(InputStream input) {
        this.input = input;
    }

    public void parse() throws IOException {
        Scanner s = new Scanner(input);
        String[] headerLines = s.findWithinHorizon("\r\n\r\n", 0).split("\r\n");
        if (headerLines.length < 1) {
            throw HttpError.badRequest();
        }

        String[] firstLine = headerLines[0].split(" ");
        if (firstLine.length < 3) {
            throw HttpError.badRequest();
        }
        method = firstLine[0].toUpperCase();
        path = firstLine[1];
        version = firstLine[2];

        for (int i = 1; i < headerLines.length; i++) {
            int pos = headerLines[i].indexOf(':');
            if (pos == -1) {
                throw HttpError.badRequest();
            }
            String name = headerLines[i].substring(0, pos);
            String value = headerLines[i].substring(pos + 1);
            headers.put(name, value);
        }
    }
}
