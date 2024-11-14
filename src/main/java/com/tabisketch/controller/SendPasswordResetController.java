package com.tabisketch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/password-reset/{token}")
public class SendPasswordResetController {
    @GetMapping()
    public String passwordReset(@PathVariable String token, Model model) {
        model.addAttribute("token", token);
        return "user/password-reset";
    }
}
