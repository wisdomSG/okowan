package com.teamproject.okowan.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/okw/view/users")
public class UserViewController {

    @GetMapping("/login-signup")
    public String loginSignup() {
        return "login-signup";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/password")
    public String password() {
        return "password";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
