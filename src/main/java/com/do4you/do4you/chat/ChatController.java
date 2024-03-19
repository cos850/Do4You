package com.do4you.do4you.chat;

import com.do4you.do4you.chat.message.MessageService;
import com.do4you.do4you.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    private final MessageService messageService;

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/chat")
    public String getHome(){
        return "chat";  // thymeleaf 템플릿을 사용하므로 추가(리액트로 옮긴 후 제거). {prefix}/chat.{suffix} 에 해당하는 view 를 찾아서 반환
    }

    @MessageMapping("/message/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public MessageDto chat(@DestinationVariable String roomId, MessageDto messageDto) {
        System.out.println("[client message]\nroomId=" + roomId + "\n" + messageDto);

        return messageService.save(messageDto);
    }
}
