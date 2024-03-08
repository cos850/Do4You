package com.do4you.do4you.dto;

import com.do4you.do4you.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private String chatRoomId;
    private String userId;
    private String partnerId;

    private User partner;
    private String lastMessage;

    @Builder.Default
    private int unreadMessageCount = 0;
}
