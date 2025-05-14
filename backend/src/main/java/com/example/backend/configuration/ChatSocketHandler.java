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

import com.example.backend.dto.websocket.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    // Store sessions by roomId -> sessionId -> session
    private final Map<String, Map<String, WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    public ChatSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String roomId = extractRoomId(session);
        if (roomId != null) {
            roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(session.getId(), session);

            // Notify room about new user (handled in client)
            sendSystemMessage(roomId, "User connected to chat");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = extractRoomId(session);
        if (roomId != null) {
            // Parse the incoming message
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

            // Set roomId if not already set
            if (chatMessage.getRoomId() == null) {
                chatMessage.setRoomId(roomId);
            }

            // Set timestamp if not already set
            if (chatMessage.getTimestamp() == null) {
                chatMessage.setTimestamp(System.currentTimeMillis());
            }

            // Default to CHAT type if not specified
            if (chatMessage.getType() == null) {
                chatMessage.setType(ChatMessage.MessageType.CHAT);
            }

            // Convert the message back to JSON
            String jsonMessage = objectMapper.writeValueAsString(chatMessage);
            TextMessage textMessage = new TextMessage(jsonMessage);

            // Broadcast to all users in the room
            Map<String, WebSocketSession> roomUsers = roomSessions.get(roomId);
            if (roomUsers != null) {
                for (WebSocketSession userSession : roomUsers.values()) {
                    userSession.sendMessage(textMessage);
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

                // Notify room about user leaving
                sendSystemMessage(roomId, "User disconnected from chat");

                // If room is empty, remove the room
                if (roomUsers.isEmpty()) {
                    roomSessions.remove(roomId);
                }
            }
        }
    }

    private String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        UriTemplate template = new UriTemplate("/ws/chat/{roomId}");
        Map<String, String> variables = template.match(path);
        return variables.get("roomId");
    }

    private void sendSystemMessage(String roomId, String content) {
        try {
            ChatMessage systemMessage = ChatMessage.builder()
                    .roomId(roomId)
                    .content(content)
                    .type(ChatMessage.MessageType.SYSTEM)
                    .timestamp(System.currentTimeMillis())
                    .build();

            String jsonMessage = objectMapper.writeValueAsString(systemMessage);
            TextMessage textMessage = new TextMessage(jsonMessage);

            Map<String, WebSocketSession> roomUsers = roomSessions.get(roomId);
            if (roomUsers != null) {
                for (WebSocketSession session : roomUsers.values()) {
                    if (session.isOpen()) {
                        session.sendMessage(textMessage);
                    }
                }
            }
        } catch (IOException e) {
            // Log error
        }
    }
}
