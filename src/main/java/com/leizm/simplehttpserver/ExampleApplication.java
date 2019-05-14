package com.leizm.simplehttpserver;

public class ExampleApplication {
    public static void main(String[] args) throws Exception {
        ServerOptions options = new ServerOptions();
        options.setAddress("0.0.0.0");
        options.setPort(3000);
        Server server = new Server(options);
        Handler handler = new Handler() {
            @Override
            public void onRequest(ServerRequest req, ServerResponse res) {
                try {
                    System.out.println(String.format("From %s", req.getConnection().getSocket().getRemoteSocketAddress()));
                    System.out.println(String.format("Request: %s %s", req.getMethod(), req.getPath()));
                    for (String name: req.getHeaders().keySet()) {
                        System.out.println(String.format("\t%s: %s", name, req.getHeader(name)));
                    }
                    byte[] body = req.getBody();
                    System.out.println(String.format("\tBody: %s", new String(body)));
                    res.end("Hello, world");
                    System.out.println("Finish");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        server.setHandler(handler);
        System.out.println(String.format("Server listening on %s:%s", options.getAddress(), options.getPort()));
        server.listen();
    }
}
