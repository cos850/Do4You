package com.do4you.do4you.dto;

import com.do4you.do4you.common.UserRole;
import com.do4you.do4you.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.do4you.do4you.utils.DateTimeUtils.dateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    private UserRole role;

    private String createdDate;
    private String lastModifiedDate;

    public User toDocument(){
        return User.builder()
                .userId(userId)
                .name(name)
                .email(email)
                .password(password)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .createdDate(user.getCreatedDate().format(dateTimeFormatter))
                .build();
    }
}
