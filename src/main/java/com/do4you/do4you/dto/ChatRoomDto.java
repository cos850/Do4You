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
    private String fromUserId;
    private String toUserId;

    private User partner;
    private String lastMessage;
}
