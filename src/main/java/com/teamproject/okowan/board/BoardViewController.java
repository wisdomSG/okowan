package com.teamproject.okowan.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/okw/view/boards")
public class BoardViewController {

    @GetMapping("/board")
    public String boardIndex() {
        return "board";
    }

    @GetMapping("/ex")
    public String ex() {
        return "ex";
    }
}

