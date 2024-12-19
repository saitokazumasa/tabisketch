package com.tabisketch.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CreatePlanController.class)
public class CreatePlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testGet() throws Exception {
        final String mailAddress = currentUserMailAddress();

        mockMvc.perform(MockMvcRequestBuilders.get("/plan/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("mailAddress"))
                .andExpect(MockMvcResultMatchers.model().attribute("mailAddress", mailAddress))
                .andExpect(MockMvcResultMatchers.view().name("plan/create"));
    }

    private static String currentUserMailAddress() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
