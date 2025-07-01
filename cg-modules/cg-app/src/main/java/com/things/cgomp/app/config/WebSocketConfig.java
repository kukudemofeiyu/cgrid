package com.things.cgomp.app.config;

import com.things.cgomp.app.handler.WebSocketHandler;
import com.things.cgomp.app.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * websocket 配置
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket/{token}")
//                .addInterceptors(new WebSocketInterceptor())
                // 允许跨域
                .setAllowedOrigins("*");
    }
}
