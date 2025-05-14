package com.example.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameSocketHandler gameSocketHandler;
    private final ChatSocketHandler chatSocketHandler;

    public WebSocketConfig(GameSocketHandler gameSocketHandler, ChatSocketHandler chatSocketHandler) {
        this.gameSocketHandler = gameSocketHandler;
        this.chatSocketHandler = chatSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameSocketHandler, "/ws/game/{roomId}")
                .setAllowedOriginPatterns("http://localhost:5173")
                .withSockJS();

        registry.addHandler(chatSocketHandler, "/ws/chat/{roomId}")
                .setAllowedOriginPatterns("http://localhost:5173");
    }
}
