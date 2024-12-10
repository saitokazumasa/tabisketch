package com.tabisketch.controller;

import com.tabisketch.service.implement.AuthMailAddressService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@WebMvcTest(AuthMailAddressController.class)
public class AuthMailAddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthMailAddressService authMailAddressService; // DIで使用している

    @ParameterizedTest
    @MethodSource("sampleToken")
    @WithMockUser
    public void getが動作するか(final String token) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/mail/confirm/" + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("mail/confirm"));
    }

    private static Stream<String> sampleToken() {
        final var token = "a2e69add-9d95-4cf1-a59b-cedbb95dcd6b";
        return Stream.of(token);
    }
}
