package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendEditPasswordForm;
import com.tabisketch.mapper.IPassWordAuthenticationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.SendEditPasswordService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SendEditPasswordServiceTest {

    @MockBean
    private IUsersMapper usersMapper;

    @MockBean
    private IPassWordAuthenticationTokensMapper passWordAuthenticationTokensMapper;

    @MockBean
    private ISendPasswordService sendPasswordService;

    @InjectMocks
    private SendEditPasswordService sendEditPasswordService;  // インジェクト

    @ParameterizedTest
    @MethodSource("sampleSendEditPasswordForm")
    @WithMockUser
    public void 動作するか(final SendEditPasswordForm sendEditPasswordForm) throws MessagingException {
        // モックの設定
        when(this.usersMapper.selectByPassword(anyString())).thenReturn(sampleUser());

        // 実行
        this.sendEditPasswordService.execute(sendEditPasswordForm);

        // 検証
        verify(this.usersMapper).selectByPassword(anyString());  // 修正
        verify(this.passWordAuthenticationTokensMapper).insertWithNewMail(any());
        verify(this.sendPasswordService).execute(any());
    }

    private static User sampleUser() {
        return User.generate(
                "sample@example.com",
                "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"
        );
    }
    private static Stream<SendEditPasswordForm> sampleSendEditPasswordForm() {
        final var sendEditPasswordForm = new SendEditPasswordForm(
                "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO",
                "$2b$12$UoCHiNsI/qKRKtgxTqN9x.gpXKOjT9XqBUjyeC74ueUF6D7Aa5t2K",
                "sample@example.com"
        );
        return Stream.of(sendEditPasswordForm);
    }
}
