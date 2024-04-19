package com.do4you.do4you.auth;

import com.do4you.do4you.dto.UserDto;
import com.do4you.do4you.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @PostMapping("/join")
    @ResponseBody
    public UserDto join(UserDto userDto){
        return userService.createUser(userDto);
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
