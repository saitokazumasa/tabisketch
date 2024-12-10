package com.tabisketch.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/edit")
public class EditUserController {
    @GetMapping
    public String get(
            final @AuthenticationPrincipal(expression = "username") String mailAddress,
            final Model model
    ) {
        model.addAttribute("mailAddress", mailAddress);
        return "user/edit/index";
    }
}
