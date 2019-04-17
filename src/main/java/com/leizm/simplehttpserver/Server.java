package com.leizm.simplehttpserver;

import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadFactory;

public class Server {
    protected ServerSocket server;
    protected ServerOptions options;
    protected ThreadFactory threadPool;
    protected Handler handler;

    public Server(ServerOptions options) throws IOException {
        this.options = options;
        InetAddress addr = InetAddress.getByName(options.getAddress());
        this.server = new ServerSocket(options.getPort(), 50, addr);
    }

    public void listen() throws IOException {
        while (true) {
            Socket socket = this.server.accept();
            this.acceptNewConnection(socket);
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    protected void acceptNewConnection(Socket socket) {
        if (threadPool == null) {
            threadPool = new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    return t;
                }
            };
        }

        ServerConnection conn = new ServerConnection(socket, handler);
        Thread t = threadPool.newThread(conn);
        t.start();
    }
}
