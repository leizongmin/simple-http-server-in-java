package com.leizm.simplehttpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class RequestParser {
    protected InputStream input;
    private String method;
    private String path;
    private String[][] headers;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String[][] getHeaders() {
        return headers;
    }

    public RequestParser(InputStream input) {
        this.input = input;
    }

    public void parse() throws IOException {
        byte[] buf = new byte[1024];
        int n;
        ArrayList<String> lines = new ArrayList<String>();
        byte[] rest = null;
        while ((n = input.read(buf)) > 0) {
            int offset = 0;
            for (int i = 0; i < n; i++) {
                if (buf[i] == '\r' && buf[i + 1] == '\n') {
                    int len = i - offset;
                    if (rest != null) {
                        len += rest.length;
                    }
                    byte[] line = new byte[len];
                    if (rest != null) {
                        Arrays.copyOfRange();
                    } else {

                    }
                }
            }
        }
    }
}
