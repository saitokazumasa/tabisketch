package com.tabisketch.service;

import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
public class SendMailServiceTest {
    @MockBean
    private JavaMailSender mailSender; // DIで使用している
    @Autowired
    private ISendMailService sendMailService;

    // NOTE: アドレスエラーは検出されない
    @ParameterizedTest
    @MethodSource("sampleMail")
    @WithMockUser(username = "sample@example.com")
    public void 動作するか(final Mail mail) throws MessagingException {
        this.sendMailService.execute(mail);
    }

    private static Stream<Mail> sampleMail() {
        final var mail = Mail.generateRegisterMail("sample@example.com", UUID.randomUUID());
        return Stream.of(mail);
    }
}
