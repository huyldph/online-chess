package com.example.backend.configuration;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;

import com.example.backend.dto.websocket.GameMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GameSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    // Store sessions by roomId -> sessionId -> session
    private final Map<String, Map<String, WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    public GameSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = extractRoomId(session);
        if (roomId != null) {
            roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = extractRoomId(session);
        if (roomId != null) {
            // Parse the incoming message
            GameMessage gameMessage = objectMapper.readValue(message.getPayload(), GameMessage.class);

            // Set roomId if not already set
            if (gameMessage.getRoomId() == null) {
                gameMessage.setRoomId(roomId);
            }

            // Set timestamp if not already set
            if (gameMessage.getTimestamp() == null) {
                gameMessage.setTimestamp(System.currentTimeMillis());
            }

            // Convert the message back to JSON
            String jsonMessage = objectMapper.writeValueAsString(gameMessage);
            TextMessage textMessage = new TextMessage(jsonMessage);

            // Broadcast to all users in the room
            Map<String, WebSocketSession> roomUsers = roomSessions.get(roomId);
            if (roomUsers != null) {
                for (WebSocketSession userSession : roomUsers.values()) {
                    // Don't send back to sender if desired
                    if (!userSession.getId().equals(session.getId())) {
                        userSession.sendMessage(textMessage);
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = extractRoomId(session);
        if (roomId != null) {
            Map<String, WebSocketSession> roomUsers = roomSessions.get(roomId);
            if (roomUsers != null) {
                roomUsers.remove(session.getId());
                // If room is empty, remove the room
                if (roomUsers.isEmpty()) {
                    roomSessions.remove(roomId);
                }
            }
        }
    }

    private String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/ws/game/{roomId}");
        Map<String, String> variables = template.match(path);
        return variables.get("roomId");
    }

    private void sendSystemMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            // Log exception
        }
    }
}
