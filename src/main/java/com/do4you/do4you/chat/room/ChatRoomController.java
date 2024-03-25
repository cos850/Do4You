package com.do4you.do4you.chat.room;

import com.do4you.do4you.dto.ChatRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/{roomId}")
    public ChatRoomDto getChatRoomById(@PathVariable String roomId, @RequestParam String userId) {
        return chatRoomService.getChatRoomById(roomId, userId);
    }

    @GetMapping("/list")
    public List<ChatRoomDto> getChatRoomsByUserId(@RequestParam String userId) {
        return chatRoomService.getChatRoomsByUserId(userId);
    }

    @PostMapping
    public ChatRoomDto createChatRoom(@RequestBody ChatRoomDto chatRoomDto) {
        return chatRoomService.createChatRoom(chatRoomDto);
    }

    @DeleteMapping("/{chatRoomId}")
    public String deleteChatRoom(@PathVariable String chatRoomId){
        chatRoomService.deleteChatRoomById(chatRoomId);
        return "ok";
    }
}
