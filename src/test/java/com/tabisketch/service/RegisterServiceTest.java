package com.tabisketch.service;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegisterServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @MockBean
    private IEncryptPasswordService encryptPasswordService;
    @MockBean
    private IMailAddressAuthTokensMapper mailAuthenticationTokensMapper;
    @MockBean
    private ISendMailService sendMailService;
    @Autowired
    private IRegisterService registerService;

    @ParameterizedTest
    @MethodSource("sampleRegisterForm")
    public void 動作するか(final RegisterForm registerForm) throws MessagingException {
        this.registerService.execute(registerForm);
        verify(this.usersMapper).insert(any());
        verify(this.encryptPasswordService).execute(anyString());
        verify(this.mailAuthenticationTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }

    private static Stream<RegisterForm> sampleRegisterForm() {
        final var registerForm = new RegisterForm(
                "sample@example.com",
                "password",
                "password"
        );
        return Stream.of(registerForm);
    }
}
