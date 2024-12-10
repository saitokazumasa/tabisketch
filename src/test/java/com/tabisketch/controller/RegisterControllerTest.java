package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IRegisterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IRegisterService registerService; // DIで使用している

    @Test
    @WithMockUser
    public void getが動作するか() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register/index"));
    }

    @ParameterizedTest
    @MethodSource("sampleRegisterForm")
    @WithMockUser
    public void postが動作するか(final RegisterForm registerForm) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("registerForm", registerForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register/send"));
    }

    @ParameterizedTest
    @MethodSource("sampleErrorRegisterForm")
    @WithMockUser
    public void フォームがバリデーションエラーになるか(final RegisterForm registerForm) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .flashAttr("registerForm", registerForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("registerForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("registerForm", registerForm))
                .andExpect(MockMvcResultMatchers.view().name("register/index"));
    }

    @Test
    @WithMockUser
    public void sendが動作するか() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/register/send"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register/send"));
    }

    private static Stream<RegisterForm> sampleRegisterForm() {
        final var registerForm = new RegisterForm(
                "sample@example.com",
                "password",
                "password"
        );
        return Stream.of(registerForm);
    }

    private static Stream<RegisterForm> sampleErrorRegisterForm() {
        // mailが未入力
        final var registerForm1 = new RegisterForm("", "password", "password");
        // passwordが未入力
        final var registerForm2 = new RegisterForm("example@mail.com", "", "password");
        // rePasswordが未入力
        final var registerForm3 = new RegisterForm("example@mail.com", "password", "");
        // passwordとrePasswordが一致しない
        final var registerForm4 = new RegisterForm("example@mail.com", "password", "pass");
        return Stream.of(registerForm1, registerForm2, registerForm3, registerForm4);
    }
}
