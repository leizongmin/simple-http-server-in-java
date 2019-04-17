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
                    System.out.println(String.format("%s %s", req.getMethod(), req.getPath()));
                    System.out.println(String.format("\t%s", String.valueOf(req.getBody())));
                    res.end("Hello, world");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        server.setHandler(handler);
        server.listen();
    }
}
