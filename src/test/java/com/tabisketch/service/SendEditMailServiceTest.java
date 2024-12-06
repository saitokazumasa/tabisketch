package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendEditMailForm;
import com.tabisketch.mapper.IMailAuthenticationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.SendEditMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SendEditMailServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @MockBean
    private IMailAuthenticationTokensMapper mailAuthenticationTokensMapper;
    @MockBean
    private ISendMailService sendMailService;

    @ParameterizedTest
    @MethodSource("動作するかのテストデータ")
    public void 動作するか(final SendEditMailForm sendEditMailForm) throws MessagingException {
        when(usersMapper.selectByMail(sendEditMailForm.getCurrentMail()))
                .thenReturn(User.generate("", ""));

        final var editMailService = new SendEditMailService(
                this.usersMapper,
                this.mailAuthenticationTokensMapper,
                this.sendMailService
        );
        editMailService.execute(sendEditMailForm);

        verify(usersMapper).selectByMail(any());
        verify(mailAuthenticationTokensMapper).insertWithNewMail(any());
        verify(sendMailService).execute(any());
    }

    private static Stream<SendEditMailForm> 動作するかのテストデータ() {
        final var e = new SendEditMailForm("sample@example.com", "sample2@example.com", "password");
        return Stream.of(e);
    }
}
