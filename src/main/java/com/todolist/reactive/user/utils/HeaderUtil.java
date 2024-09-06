package com.todolist.reactive.user.utils;

import org.springframework.web.server.ServerWebExchange;

public class HeaderUtil {
    public static String extractUsername(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst("username");
    }
}
