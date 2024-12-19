package com.tabisketch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.bean.response.CreatePlaceResponse;
import com.tabisketch.service.ICreatePlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;

@WebMvcTest(CreatePlaceAPIController.class)
public class CreatePlaceAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ICreatePlaceService __; // DIで使用

    @Test
    @WithMockUser
    public void testPost() throws Exception {
        final var createPlaceForm = new CreatePlaceForm(
                1,
                1,
                0,
                LocalTime.of(10, 0),
                LocalTime.of(11,0),
                null,
                null,
                null,
                null,
                null
        );
        final String responseJson = this.objectMapper.writeValueAsString(CreatePlaceResponse.success());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create-place")
                        .flashAttr("createPlaceForm", createPlaceForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }
}
