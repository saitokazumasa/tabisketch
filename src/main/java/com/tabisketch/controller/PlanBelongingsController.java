package com.tabisketch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("plan/belongings/{uuid}")
public class PlanBelongingsController {
    @GetMapping()
    public String get(@PathVariable("uuid") String uuid) {

        return "plan/belongings";
    }
}