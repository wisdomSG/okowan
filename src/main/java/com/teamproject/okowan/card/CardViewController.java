package com.teamproject.okowan.card;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/okw/view/cards")
public class CardViewController {

    @GetMapping("/{id}")
    public String choiceCard(@PathVariable Long id, Model model) {
        model.addAttribute("cardId", id);
        return "cardDetails";
    }
}
