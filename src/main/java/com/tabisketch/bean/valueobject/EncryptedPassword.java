package com.tabisketch.bean.valueobject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPassword {
    private final String value;
    private final BCryptPasswordEncoder passwordEncoder;

    private EncryptedPassword(final String value, final BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.value = value;
    }

    public static EncryptedPassword generate(final String value){
        final var passwordEncoder = new BCryptPasswordEncoder();
        final var encryptedPassword = passwordEncoder.encode(value);
        return new EncryptedPassword(encryptedPassword, passwordEncoder);
    }

    /**
     * 既に暗号化されたパスワードからインスタンスを生成する
     */
    public static EncryptedPassword generateFromEncryptedPassword(final String value) {
        final var passwordEncoder = new BCryptPasswordEncoder();
        return new EncryptedPassword(value, passwordEncoder);
    }

    public String value() {
        return this.value;
    }

    public boolean isMatch(final String password) {
        return this.passwordEncoder.matches(password, this.value);
    }
}
