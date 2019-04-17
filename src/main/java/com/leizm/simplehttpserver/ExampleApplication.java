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
                    System.out.println(String.format("Request: %s %s", req.getMethod(), req.getPath()));
                    for (String name: req.getHeaders().keySet()) {
                        System.out.println(String.format("\t%s: %s", name, req.getHeader(name)));
                    }
                    byte[] body = req.getBody();
                    System.out.println(String.format("\tBody: %s", new String(body)));
                    res.end("Hello, world");
                    System.out.println("Response");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        server.setHandler(handler);
        server.listen();
    }
}
