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
@Document(collection = "User")
public class User {

    @Id
    private String userId;  // MongoDBRepository 가 Id String 타입을 기본 지원해준다고해서 일단 Long 대신 String 으로 변경
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    @Builder
    public User(String userId, String name, String email, String password, String nickname, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
