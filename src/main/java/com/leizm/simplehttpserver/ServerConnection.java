package com.leizm.simplehttpserver;

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable {
    protected Socket socket;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Handler handler;

    public ServerConnection(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                ServerRequest req = new ServerRequest(in);
                req.parse();
                boolean keepAlive = false;
                if ("HTTP/1.1".equals(req.getVersion()) && req.hasHeader("content-length")) {
                    keepAlive = true;
                }

                ServerResponse res = new ServerResponse(out);
                res.setHeader("X-Powered-By", "com.leizm.simplehttpserver");
                if (keepAlive) {
                    res.setHeader("Connection", "keep-alive");
                } else {
                    res.setHeader("Connection", "close");
                }

                if (handler == null) {
                    res.setStatusCode(404);
                    res.setMessage("Not Found");
                    res.end();
                } else {
                    handler.onRequest(req, res);
                }

                if (!keepAlive) {
                    destroy();
                    break;
                }
            }
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
}
