package com.do4you.do4you.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {
    USER("ROLE_USER");

    final String key;
}
