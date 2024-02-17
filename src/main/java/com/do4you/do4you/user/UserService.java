package com.do4you.do4you.user;

import com.do4you.do4you.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) ;
    UserDto getUserById(String userId) ;
    UserDto updateUser(UserDto userDto) ;
    void deleteUserById(String userId) ;
}
