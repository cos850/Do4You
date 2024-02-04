package com.do4you.do4you.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUserById(userId);
        return "ok";
    }
}
