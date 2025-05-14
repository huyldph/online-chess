package com.example.backend.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMessage {
    private String roomId;
    private String userId;
    private String username;
    private MoveData move;
    private Long timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoveData {
        private String from; // Starting position (e.g., "e2")
        private String to; // Target position (e.g., "e4")
        private String piece; // Type of piece moved (e.g., "pawn", "knight")
        private Boolean isCapture; // Whether this move captures a piece
        private Boolean isCheck; // Whether this move results in check
        private Boolean isCheckmate; // Whether this move results in checkmate
        private String promoteTo; // For pawn promotion (e.g., "queen")
    }
}
