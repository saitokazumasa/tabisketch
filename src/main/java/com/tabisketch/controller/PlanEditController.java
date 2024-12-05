package com.tabisketch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan/edit/{uuid}")
public class PlanEditController {
    @GetMapping()
    public String get(@PathVariable String uuid) {
        return "plan/edit";
    }
}