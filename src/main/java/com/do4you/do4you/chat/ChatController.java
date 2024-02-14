package com.do4you.do4you.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Map sendMessageTest(Map message) {
        System.out.println(message);

        return Map.of("content", message.get("name"));
    }






}
