package com.do4you.do4you.chat.message;

import com.do4you.do4you.dto.MessageDto;
import com.do4you.do4you.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getPage(String roomId, MessageDto last, int pageSize) {
        return messageRepository.page(roomId, last.toDocument(), pageSize).stream()
                .map(MessageDto::toDto)
                .collect(Collectors.toList());
    }

    public MessageDto save(MessageDto dto) {
        Message saved = messageRepository.save(dto.toDocument());

        return MessageDto.toDto(saved);
    }

    public void remove(String id){
        messageRepository.deleteById(id);
    }

}
