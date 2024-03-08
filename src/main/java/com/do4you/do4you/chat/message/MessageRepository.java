package com.do4you.do4you.chat.message;

import com.do4you.do4you.model.Message;

import java.util.List;

public interface MessageRepository {

    Message save(Message message);
    void deleteById(String messageId);
    List<Message> page(String roomId, Message lastId, int pageSize);

}
