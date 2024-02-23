package com.do4you.do4you.chat.message;

import com.do4you.do4you.dto.MessageDto;
import com.do4you.do4you.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getPage(String userId, String chatRoomDto) {
        return null;
    }

    public MessageDto save(MessageDto dto) {
        Message saved = messageRepository.save(dto.toDocument());

        return MessageDto.toDto(saved);
    }

    public void remove(String id){
        messageRepository.deleteById(id);
    }

}
