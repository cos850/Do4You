package com.do4you.do4you.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {

    @MessageMapping("/message/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public Map sendMessageTest(@DestinationVariable String roomId, Map message) {
        System.out.println("client message\nroomId=" + roomId + "\n" + message);

        return message;
    }

}
