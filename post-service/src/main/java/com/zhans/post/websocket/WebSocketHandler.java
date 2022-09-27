package com.zhans.post.websocket;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebsocketMessageHandler messageHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String[] userIdQuery = Objects.requireNonNull(session.getUri()).getQuery().split("=");
        Long userId = Long.valueOf(userIdQuery[1]);
        System.out.println("Connect: " + userId);
        messageHandler.addSessionToPool(userId, session);
        messageHandler.sendMessageToUser(userId, "Hello user " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        String[] userIdQuery = Objects.requireNonNull(session.getUri()).getQuery().split("=");
        Long userId = Long.valueOf(userIdQuery[1]);
        System.out.println("Close: " + userId);
        messageHandler.removeSessionFromPool(userId, session);
    }
}
