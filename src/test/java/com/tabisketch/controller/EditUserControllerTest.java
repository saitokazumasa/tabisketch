package com.tabisketch.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@WebMvcTest(EditUserController.class)
public class EditUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("sampleMailAddress")
    @WithMockUser(username = "sample@example.com")
    public void getが動作するか(final String mailAddress) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("mailAddress"))
                .andExpect(MockMvcResultMatchers.model().attribute("mailAddress", mailAddress))
                .andExpect(MockMvcResultMatchers.view().name("user/edit/index"));
    }

    private static Stream<String> sampleMailAddress() {
        final var mailAddress = "sample@example.com";
        return Stream.of(mailAddress);
    }
}