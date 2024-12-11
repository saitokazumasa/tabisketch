package com.tabisketch.service.implement;

import com.tabisketch.service.IEncryptPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptPasswordService implements IEncryptPasswordService {
    private final PasswordEncoder passwordEncoder;

    public EncryptPasswordService(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String execute(final String password) {
        return passwordEncoder.encode(password);
    }
}
