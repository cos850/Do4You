package com.do4you.do4you.chat;

import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 관리할 세션
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    // 웹 소켓 연결
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("웹소켓 연결: " + session.getUri());
        
        sessions.put(session.getId(), session);

        MessageDto messageDto = MessageDto.builder()
                .messageId(session.getId())
                .sendAt("now")
                .content("사용자가 연결되었습니다.")
                .build();

        // messageDto DB save

        // 저장된 모든 세션에 사용자 연결 알림 전송 (본인 제외)
        sessions.values().forEach(s-> {
            if(!s.getId().equals(messageDto.getMessageId())) {
                try {
                    s.sendMessage(new TextMessage(messageDto.getContent()));
                } catch (IOException e) {
                    System.out.println("오류 발생 !! " + e.getLocalizedMessage());
//                    throw new RuntimeException(e);
                }
            }
        });

        super.afterConnectionEstablished(session);
    }

    // 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("데이터 통신: " + session.getUri() + ", " + message.toString());

        MessageDto messageDto = MessageDto.builder()
                .messageId(session.getId())
                .sendAt("now")
                .content(message.getPayload())
                .build();

        // messageDto DB save

        // 저장된 모든 세션에 데이터 전송 (본인 제외)
        sessions.values().forEach(s-> {
            if(!s.getId().equals(messageDto.getMessageId())) {
                try {
                    s.sendMessage(new TextMessage(messageDto.getContent()));
                } catch (IOException e) {
                    System.out.println("오류 발생 !! " + e.getLocalizedMessage());
//                    throw new RuntimeException(e);
                }
            }
        });
    }

    // 웹소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("웹소켓 종료: " + session.getUri() + ", " + status);

        // 연결 종료 시 세션 삭제
        sessions.remove(session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("웹소켓 오류");
        super.handleTransportError(session, exception);
    }
}
