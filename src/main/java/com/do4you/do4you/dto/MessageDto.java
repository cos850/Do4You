package com.do4you.do4you.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String messageId;
    private String roomId;
    private String userId;
    private String content;
    private String sendAt;
    private boolean isChecked;
}
