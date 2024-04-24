package com.do4you.do4you.model;

import com.do4you.do4you.common.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@Document(collection = "User")
public class User implements Persistable {

    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;

    private UserRole role;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Builder
    public User(String userId, String name, String email, String password, String nickname, String phoneNumber, UserRole role, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
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

    public void setRole(UserRole role){
        this.role = role;
    }

    @Override
    public Object getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
