package com.tabisketch.controller;

import com.tabisketch.bean.form.SendEditMailForm;
import com.tabisketch.service.IIsExistMailService;
import com.tabisketch.service.IIsMatchPasswordService;
import com.tabisketch.service.ISendEditMailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(SendEditMailController.class)
public class SendEditMailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IIsMatchPasswordService isMatchPasswordService;
    @MockBean
    private IIsExistMailService isExistMailService;
    @MockBean
    private ISendEditMailService sendEditMailService; // DIで使用している

    @Test
    @WithMockUser(username = "sample@example.com")
    public void getが動作するか() throws Exception {
        final var mail = getCurrentMail();
        final var sendEditMailForm = SendEditMailForm.generate(mail);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/edit/mail"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("sendEditMailForm"))
                .andExpect(MockMvcResultMatchers.model().attribute("sendEditMailForm", sendEditMailForm))
                .andExpect(MockMvcResultMatchers.view().name("user/edit/mail/index"));
    }

    private String getCurrentMail() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @ParameterizedTest
    @MethodSource("postが動作するかのテストデータ")
    @WithMockUser(password = "$2a$10$G7Emd1ALL6ibttkgRZtBZeX6Qps6lgEGKq.njouwtiuE4uvjD2YMO")
    public void postが動作するか(final SendEditMailForm sendEditMailForm) throws Exception {
        when(this.isMatchPasswordService.execute(any())).thenReturn(true);
        when(this.isExistMailService.execute(any())).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/edit/mail")
                        .flashAttr("sendEditMailForm", sendEditMailForm)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/edit/mail/send"));
    }

    private static Stream<SendEditMailForm> postが動作するかのテストデータ() {
        final var e1 = new SendEditMailForm("sample@example.com", "sample2@example.com", "password");
        return Stream.of(e1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"sample2@example.com"})
    @WithMockUser
    public void sendが動作するか(final String mail) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/edit/mail/send")
                        .flashAttr("mail", mail)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/edit/mail/send"));
    }
}
