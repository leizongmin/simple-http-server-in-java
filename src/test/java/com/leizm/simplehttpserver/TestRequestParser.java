package com.leizm.simplehttpserver;


import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestRequestParser {
    @Test
    public void testParse() throws Exception {
        String data = "POST /hello?a=1&b=2 HTTP/1.1\r\nContent-Type: application/json\r\n\r\n{\"msg\":\"hello, world\"}";
        InputStream input = new ByteArrayInputStream(data.getBytes());
        RequestParser p = new RequestParser(input);
        p.parse();
        Assert.assertEquals("POST", p.getMethod());
        Assert.assertEquals("/hello?a=1&b=2", p.getPath());
        Assert.assertEquals("application/json", p.getHeaders().get("Content-Type"));
        Assert.assertEquals("{\"msg\":\"hello, world\"}", new BufferedReader(new InputStreamReader(input)).readLine());
    }
}
