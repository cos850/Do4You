package com.do4you.do4you.dto;

import com.do4you.do4you.chat.message.MessageCommon;
import com.do4you.do4you.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.do4you.do4you.chat.message.MessageCommon.messageDateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String messageId;
    private String chatRoomId;
    private String userId;
    private String content;
    private String sendAt;

    @Builder.Default
    private boolean isChecked = false;

    public Message toDocument(){
        return Message.builder()
                .messageId(messageId)
                .roomId(chatRoomId)
                .userId(userId)
                .content(content)
                .sendAt(LocalDateTime.parse(sendAt, messageDateTimeFormatter))
                .isChecked(isChecked)
                .build();
    }

    public static MessageDto toDto(Message doc){
        return MessageDto.builder()
                .messageId(doc.getMessageId())
                .chatRoomId(doc.getRoomId())
                .userId(doc.getUserId())
                .content(doc.getContent())
                .sendAt(doc.getSendAt().format(messageDateTimeFormatter))
                .isChecked(doc.isChecked())
                .build();
    }
}
