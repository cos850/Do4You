package com.do4you.do4you.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@NoArgsConstructor
@Document(collection = "ChatRoom")
public class ChatRoom {
    @Id
    private String chatRoomId;
    private String fromUserId;
    private String toUserId;

    @Builder
    public ChatRoom(String chatRoomId, String fromUserId, String toUserId) {
        this.chatRoomId = chatRoomId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
