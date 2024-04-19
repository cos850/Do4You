package com.do4you.do4you.home;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/check")
    public String check(){
        return "index";
    }

    @GetMapping("/role_admin")
    public String checkAdmin(){
        return "index";
    }

    @GetMapping("/role_user")
    public String checkUser(){
        System.out.println("check user!");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "index";
    }

    @GetMapping("/loginError")
    public String loginError(){
        return "loginError";
    }

}
