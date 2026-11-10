package com.instantdoctor.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SignalingWebSocketHandler extends TextWebSocketHandler {

    // Active rooms: roomId -> Map<sessionId, WebSocketSession>
    private final Map<String, Map<String, WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebRTC Signaling client connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // Forward SDP Offer / Answer / ICE Candidate to the peer in the same room
        rooms.values().forEach(room -> {
            if (room.containsKey(session.getId())) {
                room.forEach((peerSessionId, peerSession) -> {
                    if (!peerSessionId.equals(session.getId()) && peerSession.isOpen()) {
                        try {
                            peerSession.sendMessage(new TextMessage(payload));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        rooms.values().forEach(room -> room.remove(session.getId()));
        System.out.println("WebRTC Signaling client disconnected: " + session.getId());
    }
}
