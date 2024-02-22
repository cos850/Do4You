package com.do4you.do4you.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@NoArgsConstructor
@Document(collection = "ChatRoom")
// 복합 인덱스
// 채팅방ID와 사용자ID 조합이 컬렉션 내에서 고유하게 설정한다.
// 참고: https://docs.spring.io/spring-data/mongodb/reference/mongodb/mapping/mapping-index-management.html
@CompoundIndex(name = "room_idx", def = "{'userId': 1, 'partnerId': 1}", unique = true)
public class ChatRoom {

    @Indexed
    private String chatRoomId;
    private String userId;
    private String partnerId;

    @Builder
    public ChatRoom(String chatRoomId, String userId, String partnerId) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.partnerId = partnerId;
    }
}
