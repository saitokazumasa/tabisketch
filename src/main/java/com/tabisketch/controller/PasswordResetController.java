package com.tabisketch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user/password-reset")
public class PasswordResetController {
    @GetMapping()
    public String passwordReset() {
        return "user/password-reset";
    }
}
