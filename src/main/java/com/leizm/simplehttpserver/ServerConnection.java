package com.leizm.simplehttpserver;

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable {
    protected Socket socket;
    protected BufferedReader in;
    protected BufferedWriter out;

    public ServerConnection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            tryParseRequestHeader();
        } catch (IOException e) {
            System.out.println(e.toString());
            destroy();
        }
    }

    protected void destroy() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    protected void tryParseRequestHeader() throws IOException {
        String first = in.readLine();
        String line;
        while (true) {
            line = in.readLine();
        }
    }
}
