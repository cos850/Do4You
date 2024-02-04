package com.do4you.do4you.user;

public interface UserService {
    UserDto createUser(UserDto userDto) ;
    UserDto getUserById(String userId) ;
    UserDto updateUser(UserDto userDto) ;
    void deleteUserById(String userId) ;
}
