package com.tabisketch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(EditUserController.class)
public class EditUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(username = "sample@example.com")
    public void getが動作するか() throws Exception {
        final String mail = getCurrentMail();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("mail"))
                .andExpect(MockMvcResultMatchers.model().attribute("mail", mail))
                .andExpect(MockMvcResultMatchers.view().name("user/edit/index"));
    }
    private String getCurrentMail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}