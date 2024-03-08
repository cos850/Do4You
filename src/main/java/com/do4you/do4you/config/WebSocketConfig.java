package com.do4you.do4you.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // stomp 통신을 통해 클라이언트의 요청을 수신할 경로의 prefix
        // 해당 prefix 경로로 Client의 요청이 들어오면 @MessageMapping 으로 매핑해준다.
        registry.setApplicationDestinationPrefixes("/chat/client");

        // SimpleBroker 가 수신할 경로의 prefix 설정
        // 브로커는 아래 설정한 경로를 구독하고 있는 클라이언트에게 메세지를 전달한다.
        registry.enableSimpleBroker("/chat/room");
    }

    /**
     * SocketJS Client와 커넥션을 생성할 경로 설정
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/register").withSockJS();
    }
}

