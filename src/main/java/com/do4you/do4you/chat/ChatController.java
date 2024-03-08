package com.do4you.do4you.chat;

import com.do4you.do4you.chat.message.MessageService;
import com.do4you.do4you.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final MessageService messageService;

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public MessageDto chat(@DestinationVariable String roomId, MessageDto messageDto) {
        System.out.println("[client message]\nroomId=" + roomId + "\n" + messageDto);

        return messageService.save(messageDto);
    }
}
