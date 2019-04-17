package com.leizm.simplehttpserver;

import org.junit.Assert;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class TestUtils {
    @Test
    public void testSearchHeader() throws Exception {
        String text = "POST /hello?a=1&b=2 HTTP/1.1\r\nContent-Type: application/json\r\n\r\n{\"msg\":\"hello, world\"}";
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(text.getBytes()));
        byte[] header = Utils.searchHeader(input);
        Assert.assertEquals("POST /hello?a=1&b=2 HTTP/1.1\r\nContent-Type: application/json\r\n\r\n", new String(header));
        Assert.assertEquals("{\"msg\":\"hello, world\"}", new String(IOUtils.readFully(input, -1, false)));
    }
}
