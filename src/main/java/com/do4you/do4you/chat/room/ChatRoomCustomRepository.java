package com.do4you.do4you.chat.room;

import com.do4you.do4you.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomCustomRepository {

    List<ChatRoomDto> getUserChatRoom(String userId);
}
