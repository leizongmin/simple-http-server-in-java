package com.leizm.simplehttpserver;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class ServerRequest {
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

    public String getVersion() {
        return version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public ServerRequest(InputStream input) {
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
        version = firstLineBlocks[2].toUpperCase();

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
            String name = line.substring(0, pos).trim().toLowerCase();
            String value = line.substring(pos + 1).trim();
            headers.put(name, value);
        }
        input.reset();
        input.skip(offset);
    }

    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    public boolean hasHeader(String name) {
        return headers.containsKey(name.toLowerCase());
    }

    public byte[] getBody() throws IOException {
        if (hasHeader("content-length")) {
            int len = Integer.valueOf(getHeader("content-length"));
            byte[] buf = new byte[len];
            return buf;
        } else {
            return IOUtils.readFully(input, -1, false);
        }
    }
}
