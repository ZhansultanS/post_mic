package com.zhans.post.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhans.post.dto.MessageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebsocketMessageHandler {

    private final Map<Long, Set<WebSocketSession>> websocketSessionPool = new ConcurrentHashMap<>();

    public void addSessionToPool(Long userId, WebSocketSession session) {
        Set<WebSocketSession> userSessions = websocketSessionPool.get(userId);

        if (userSessions != null) {
            userSessions.add(session);
            websocketSessionPool.put(userId, userSessions);
        } else {
            Set<WebSocketSession> newUserSessions = new HashSet<>();
            newUserSessions.add(session);
            websocketSessionPool.put(userId, newUserSessions);
        }
    }

    public void removeSessionFromPool(Long userId, WebSocketSession session) {
        Set<WebSocketSession> userSessions = websocketSessionPool.get(userId);

        if (userSessions != null) {
            for (WebSocketSession sessionItem : userSessions) {
                if (sessionItem.equals(session)) {
                    userSessions.remove(session);
                }
            }
        }
        websocketSessionPool.put(userId, userSessions);
    }

    public void sendMessageToUser(Long userId, String message) throws IOException {
        Set<WebSocketSession> userSessions = websocketSessionPool.get(userId);

        if (userSessions == null) return;

        MessageDto websocketMessage = new MessageDto(message);
        String msg = new ObjectMapper().writeValueAsString(websocketMessage);
        TextMessage textMessage = new TextMessage(msg);
        for (WebSocketSession session : userSessions) {
            session.sendMessage(textMessage);
        }
    }
}
