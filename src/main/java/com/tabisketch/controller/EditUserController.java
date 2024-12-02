package com.tabisketch.controller;

import com.tabisketch.bean.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/edit")
public class EditUserController {

    @GetMapping
    public String get(Model model) {
        //Todo　仮の処理
        User item = new User();
        item.setMail("user@example.com");
        model.addAttribute("item", item);
        return "user/edit/index";
    }
}
