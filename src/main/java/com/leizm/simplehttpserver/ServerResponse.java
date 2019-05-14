package com.leizm.simplehttpserver;

import lombok.Data;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@Data
public class ServerResponse {
    private ServerConnection connection;
    private OutputStream output;

    private HashMap<String, String> headers = new HashMap<String, String>();

    private int statusCode = 200;
    private String message = "OK";

    public ServerResponse(ServerConnection connection, OutputStream output) {
        this.connection = connection;
        this.output = output;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    protected void writeHead() throws IOException {
        output.write(String.format("HTTP/1.1 %d %s\r\n", statusCode, message).getBytes());
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
