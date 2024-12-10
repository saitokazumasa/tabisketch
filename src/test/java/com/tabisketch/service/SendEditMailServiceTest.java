package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendEditMailForm;
import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SendEditMailServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @MockBean
    private IMailAddressAuthTokensMapper mailAuthenticationTokensMapper;
    @MockBean
    private ISendMailService sendMailService;
    @Autowired
    private ISendEditMailService sendEditMailService;

    @ParameterizedTest
    @MethodSource("sampleSendEditMailForm")
    @WithMockUser
    public void 動作するか(final SendEditMailForm sendEditMailForm) throws MessagingException {
        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(sampleUser());

        this.sendEditMailService.execute(sendEditMailForm);
        verify(this.usersMapper).selectByMailAddress(anyString());
        verify(this.mailAuthenticationTokensMapper).insertWithNewMail(any());
        verify(this.sendMailService).execute(any());
    }

    private static User sampleUser() {
        return User.generate(
                "sample@example.com",
                "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"
        );
    }

    private static Stream<SendEditMailForm> sampleSendEditMailForm() {
        final var sendEditMailFrom = new SendEditMailForm(
                "sample@example.com",
                "sample2@example.com",
                "password"
        );
        return Stream.of(sendEditMailFrom);
    }
}
