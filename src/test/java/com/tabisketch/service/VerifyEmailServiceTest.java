package com.tabisketch.service;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VerifyEmailServiceTest {
    @Autowired
    private IVerifyEmailService verifyEmailService;
    @MockitoBean
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @MockitoBean
    private IUsersMapper usersMapper;

    @Test
    public void testExecute() {
        final var evToken = ExampleEmailVerificationToken.gen();
        when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(evToken);
        when(this.usersMapper.updateEmailVerified(anyInt(), anyBoolean())).thenReturn(1);
        when(this.emailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = evToken.getUuid().toString();
        this.verifyEmailService.execute(uuid);

        verify(this.emailVerificationTokensMapper).selectByUUID(any());
        verify(this.usersMapper).updateEmailVerified(anyInt(), anyBoolean());
        verify(this.emailVerificationTokensMapper).delete(any());
    }

    @ParameterizedTest
    @MethodSource("expirationTestData")
    public void testExpiration(final EmailVerificationToken evToken) {
        when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(evToken);
        when(this.usersMapper.updateEmailVerified(anyInt(), anyBoolean())).thenReturn(1);
        when(this.emailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = evToken.getUuid().toString();

        try {
            this.verifyEmailService.execute(uuid);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            assert true;
            return;
        }
        assert false;
    }

    private static Stream<Object> expirationTestData() {
        final var stream = Stream.builder();
//        stream.add(new EmailVerificationToken(UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"), 1, LocalDateTime.now().minusMinutes(29))); // 通らない
        stream.add(new EmailVerificationToken(UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"), 1, LocalDateTime.now().minusMinutes(30)));
        stream.add(new EmailVerificationToken(UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"), 1, LocalDateTime.now().minusMinutes(31)));
        return stream.build();
    }
}
