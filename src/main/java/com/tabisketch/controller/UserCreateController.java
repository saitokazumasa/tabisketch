package com.tabisketch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/create")
public class UserCreateController {
    @GetMapping()
    public String create() {
        return "user/create";
    }
}
