package com.do4you.do4you.chat;

import com.do4you.do4you.chat.message.MessageService;
import com.do4you.do4you.chat.room.ChatRoomService;
import com.do4you.do4you.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatController(MessageService messageService, ChatRoomService chatRoomService) {
        this.messageService = messageService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/chat")
    public String getHome(@RequestParam String userId, Model model){
        model.addAttribute("chatRooms", chatRoomService.getChatRoomsByUserId(userId));

        System.out.println("chatRooms: " + model.getAttribute("chatRooms"));
        return "chat";
    }

    @MessageMapping("/message/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public MessageDto chat(@DestinationVariable String roomId, MessageDto messageDto) {
        System.out.println("[client message]\nroomId=" + roomId + "\n" + messageDto);

        return messageService.save(messageDto);
    }
}
