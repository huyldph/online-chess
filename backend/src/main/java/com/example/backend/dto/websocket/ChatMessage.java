package com.example.backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomId;
    private String userId;
    private String username;
    private String content;
    private Long timestamp;
    private MessageType type;

    public enum MessageType {
        CHAT, // Regular user message
        JOIN, // User joined the room
        LEAVE, // User left the room
        SYSTEM // System notification
    }
}
