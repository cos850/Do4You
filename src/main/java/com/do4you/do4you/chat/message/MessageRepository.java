package com.do4you.do4you.chat.message;

import com.do4you.do4you.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {

}
