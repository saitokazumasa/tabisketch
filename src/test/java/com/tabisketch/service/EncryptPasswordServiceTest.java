package com.tabisketch.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootTest
public class EncryptPasswordServiceTest {
    @Autowired
    private IEncryptPasswordService encryptPasswordService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ParameterizedTest
    @MethodSource("samplePassword")
    public void 動作するか(final String password) {
        final var encryptedPassword = this.encryptPasswordService.execute(password);
        assert passwordEncoder.matches(password, encryptedPassword);
    }

    private static Stream<String> samplePassword() {
        final var password = "password";
        return Stream.of(password);
    }
}
