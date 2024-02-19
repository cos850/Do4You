package com.do4you.do4you.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/chat/message")
    @SendTo("/chat/room/{roomId}")
    public Map sendMessageTest(Map message) {
        System.out.println(message);

        return Map.of("content", message.get("message"));
    }

}
