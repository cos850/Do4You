package com.do4you.do4you.chat.room;

import com.do4you.do4you.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String>, ChatRoomCustomRepository {

    Optional<ChatRoom> findByChatRoomIdAndUserId(String chatRoomId, String userId);

}
