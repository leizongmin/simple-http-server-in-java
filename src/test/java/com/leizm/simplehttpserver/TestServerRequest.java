package com.leizm.simplehttpserver;


import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestServerRequest {
    @Test
    public void testParse() throws Exception {
        String data = "POST /hello?a=1&b=2 HTTP/1.1\r\nContent-Type: application/json\r\n\r\n{\"msg\":\"hello, world\"}";
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(data.getBytes()));
        ServerRequest req = new ServerRequest(null, input);
        req.parse();
        Assert.assertEquals("POST", req.getMethod());
        Assert.assertEquals("/hello?a=1&b=2", req.getPath());
        Assert.assertEquals("application/json", req.getHeader("Content-Type"));
        Assert.assertEquals("{\"msg\":\"hello, world\"}", new String(req.getBody()));
    }
}
