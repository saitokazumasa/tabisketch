package com.tabisketch.service;

import com.tabisketch.bean.entity.MailAddressAuthToken;
import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthMailAddressServiceTest {
    @MockBean
    private IMailAddressAuthTokensMapper mailAddressAuthTokensMapper;
    @MockBean
    private IUsersMapper usersMapper;
    @Autowired
    private IAuthMailAddressService authMailAddressService;

    @ParameterizedTest
    @MethodSource("sampleMailAddressAuthToken")
    public void 動作するか(final MailAddressAuthToken mailAddressAuthToken) {
        when(this.mailAddressAuthTokensMapper.selectByToken(any())).thenReturn(mailAddressAuthToken);

        assert this.authMailAddressService.execute(mailAddressAuthToken.getToken().toString());
        verify(this.mailAddressAuthTokensMapper).selectByToken(any());
        verify(this.usersMapper).updateMailVerified(anyInt(), anyBoolean());
        verify(this.mailAddressAuthTokensMapper).deleteById(anyInt());
    }

    @ParameterizedTest
    @MethodSource("sampleMailAddressAuthTokenWithNewMailAddress")
    public void 新しいメールアドレスが指定された時更新しているか(final MailAddressAuthToken mailAddressAuthToken) {
        when(this.mailAddressAuthTokensMapper.selectByToken(any())).thenReturn(mailAddressAuthToken);

        assert this.authMailAddressService.execute(mailAddressAuthToken.getToken().toString());
        verify(this.mailAddressAuthTokensMapper).selectByToken(any());
        verify(this.usersMapper).updateMailVerified(anyInt(), anyBoolean());
        verify(this.usersMapper).updateMailAddress(anyInt(), anyString());
        verify(this.mailAddressAuthTokensMapper).deleteById(anyInt());
    }

    @ParameterizedTest
    @MethodSource("sampleMailAddressAuthToken")
    public void トークンが存在しない時falseが返る(final MailAddressAuthToken mailAddressAuthToken) {
        when(this.mailAddressAuthTokensMapper.selectByToken(any())).thenReturn(null);

        assert !this.authMailAddressService.execute(mailAddressAuthToken.getToken().toString());
        verify(this.mailAddressAuthTokensMapper).selectByToken(any());
    }

    private static Stream<MailAddressAuthToken> sampleMailAddressAuthToken() {
        final var mailAddressAuthToken = new MailAddressAuthToken(
                1,
                UUID.randomUUID(),
                "",
                1,
                LocalDateTime.now()
        );
        return Stream.of(mailAddressAuthToken);
    }

    private static Stream<MailAddressAuthToken> sampleMailAddressAuthTokenWithNewMailAddress() {
        final var mailAddressAuthToken = new MailAddressAuthToken(
                1,
                UUID.randomUUID(),
                "sample@example.com",
                1,
                LocalDateTime.now()
        );
        return Stream.of(mailAddressAuthToken);
    }
}
