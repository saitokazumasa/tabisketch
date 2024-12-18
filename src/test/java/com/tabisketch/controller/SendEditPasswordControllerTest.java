package com.tabisketch.controller;

import com.tabisketch.bean.form.SendEditPasswordForm;
import com.tabisketch.service.IIsExistMailService;
import com.tabisketch.service.IIsMatchPasswordService;
import com.tabisketch.service.ISendEditMailService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(SendEditPasswordController.class)
public class SendEditPasswordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IIsMatchPasswordService isMatchPasswordService;
    @MockBean
    private IIsExistMailService isExistMailService;
    @MockBean
    private ISendEditMailService sendEditMailService;

    @ParameterizedTest
    @MethodSource("sampleInitSendEditPasswordForm")
    @WithMockUser(username = "sample@example.com")
    public void getが動作するか{final SendEditPasswordForm sendEditPasswordForm} throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("user/edit/password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("sendEditPasswordForm"))
                .andExpect(MockMvcResultMatchers.view().name("user/edit/mail/index"));
    }

    @ParameterizedTest
    @MethodSource("sampleSendPasswordForm")
    @WithMockUser(username = "sample@example.com",password = "$2a$10$G7Emd1ALL6ibttkgRZtBZeX6Qps6lgEGKq.njouwtiuE4uvjD2YMO")
    public void postが動作するか(final SendEditPasswordForm sendEditPasswordForm) throws Exception {
        when (this.isMatchPasswordService.execute(any())).thenReturn(true);
        when(this.isExistMailService.execute(any())).thenReturn(false);

        this.mockMvc.perform(MockMvcRequestBuilders.post("user/edit/password"
                .post("/user/edit/password")
        ))

    }


}
