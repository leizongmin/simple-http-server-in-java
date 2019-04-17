package com.leizm.simplehttpserver;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class RequestParser {
    protected InputStream input;
    private String method;
    private String path;
    private String version;
    private HashMap<String, String> headers = new HashMap<String, String>();

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String version() {
        return version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public RequestParser(InputStream input) {
        this.input = input;
    }

    public void parse() throws IOException {
        long offset = 0;
        Scanner s = new Scanner(input).useDelimiter("\r\n");
        String firstLine = s.nextLine();
        String[] firstLineBlocks = firstLine.split(" ");
        if (firstLineBlocks.length < 3) {
            throw HttpError.badRequest();
        }
        offset += firstLine.length() + 2;
        method = firstLineBlocks[0].toUpperCase();
        path = firstLineBlocks[1];
        version = firstLineBlocks[2];

        while (s.hasNext()) {
            String line = s.nextLine();
            offset += line.length() + 2;
            if (line.length() == 0) {
                break;
            }
            int pos = line.indexOf(':');
            if (pos == -1) {
                throw HttpError.badRequest();
            }
            String name = line.substring(0, pos).trim();
            String value = line.substring(pos + 1).trim();
            headers.put(name, value);
        }
        input.reset();
        input.skip(offset);
    }
}
