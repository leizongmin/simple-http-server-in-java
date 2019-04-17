package com.leizm.simplehttpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class ServerResponse {
    protected OutputStream output;

    private HashMap<String, String> headers;

    private int statusCode;
    private String message = "OK";

    public ServerResponse(OutputStream output) {
        this.output = output;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected void writeHead() throws IOException {
        output.write(String.format("%d %s\r\n", statusCode, message).getBytes());
        for (String key : headers.keySet()) {
            output.write(String.format("%s: %s\r\n", key, headers.get(key)).getBytes());
        }
        output.write("\r\n".getBytes());
    }

    public void end() throws IOException {
        headers.put("Content-Length", "0");
        writeHead();
    }

    public void end(byte[] buf) throws IOException {
        headers.put("Content-Length", String.valueOf(buf.length));
        writeHead();
        if (buf != null) {
            output.write(buf);
        }
    }

    public void end(String text) throws IOException {
        end(text.getBytes());
    }
}
