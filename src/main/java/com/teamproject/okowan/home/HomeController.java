package com.teamproject.okowan.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        return "board";
    }

    // token 검사용 API
    @ResponseBody
    @PostMapping("/test/tokenTest")
    public void tokenCheck() {
    }
}
