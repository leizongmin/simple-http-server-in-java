package com.leizm.simplehttpserver;

import sun.misc.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class ServerRequest {
    protected DataInputStream in;
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

    public ServerRequest(DataInputStream in) {
        this.in = in;
    }

    public void parse() throws IOException {
        String[] lines = new String(Utils.searchHeader(in)).split("\r\n");
        String firstLine = lines[0];
        String[] firstLineBlocks = firstLine.split(" ");
        if (firstLineBlocks.length < 3) {
            throw HttpError.badRequest();
        }
        method = firstLineBlocks[0].toUpperCase();
        path = firstLineBlocks[1];
        version = firstLineBlocks[2].toUpperCase();

        String line;
        String name;
        String value;
        for (int i = 1; i < lines.length; i++) {
            line = lines[i];
            if (line == null || line.length() == 0) {
                break;
            }
            int pos = line.indexOf(':');
            if (pos == -1) {
                throw HttpError.badRequest();
            }
            name = line.substring(0, pos).trim().toLowerCase();
            value = line.substring(pos + 1).trim();
            headers.put(name, value);
//            System.out.println(String.format("%s: %s", name, value));
        }
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
            in.read(buf);
            return buf;
        } else if (method.equals("GET") || method.equals("HEAD")) {
            return new byte[0];
        } else {
            return IOUtils.readFully(in, -1, false);
        }
    }
}
