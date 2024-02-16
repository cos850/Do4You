package com.do4you.do4you.chat.room;

import com.do4you.do4you.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    @Query("{$or: [{fromUserId: ?0}, {toUserId:  ?0}]}")
    List<ChatRoom> findByFromUserIdOrToUserId(String userId);
}
