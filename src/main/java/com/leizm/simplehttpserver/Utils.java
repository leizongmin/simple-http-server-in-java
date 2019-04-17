package com.leizm.simplehttpserver;

import java.io.DataInputStream;
import java.io.IOException;

public final class Utils {
    public final static byte[] searchHeader(DataInputStream input) throws IOException {
        byte[] buf = new byte[1024 * 8];
        int size = 0;
        byte b;
        int n = 0;
        while (true) {
            b = input.readByte();
            buf[size++] = b;
            if (b == '\r' && (n == 0 || n == 2)) {
                n++;
            } else if (b == '\n' && (n == 1 || n == 3)) {
                n++;
                if (n == 4) {
                    break;
                }
            } else {
                n = 0;
            }
        }
        byte[] header = new byte[size];
        System.arraycopy(buf, 0, header, 0, size);
        return header;
    }
}
