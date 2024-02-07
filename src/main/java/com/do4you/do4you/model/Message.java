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
@Document(collection = "Message")
public class Message {

    @Id
    private String messageId;
    private String roomId;
    private String userId;
    private String content;
    private String sendAt;
    private String isChecked;

    @Builder
    public Message(String messageId, String roomId, String userId, String content, String sendAt, String isChecked) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.userId = userId;
        this.content = content;
        this.sendAt = sendAt;
        this.isChecked = isChecked;
    }

}
