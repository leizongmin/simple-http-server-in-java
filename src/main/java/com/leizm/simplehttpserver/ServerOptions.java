package com.leizm.simplehttpserver;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerOptions {
    private int port;
    private String address;
    private int maxConnections;
}
