package com.do4you.do4you.chat.message;

import com.do4you.do4you.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    public MessageDto save(@RequestBody MessageDto messageDto){
        return messageService.save(messageDto);
    }

    @GetMapping("/recent/{roomId}")
    public List<MessageDto> getRecentMessages(@PathVariable String roomId){
        return messageService.getPage(roomId, null, 10);
    }

    @GetMapping("/page/{roomId}")
    public List<MessageDto> getRecentMessages(@PathVariable String roomId, @RequestBody MessageDto message){
        return messageService.getPage(roomId, message, 10);
    }
}
