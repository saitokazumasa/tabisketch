package com.tabisketch.service;

import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.EditPasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class EditPasswordServiceTest {

    @Mock
    private IUsersMapper usersMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private EditPasswordService editPasswordService;

    // テストの前にMockitoの初期化を行う
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // モックの初期化
    }

    @Test
    public void パスワードが空の場合() {
        String mailAddress = "user@example.com";
        String newPassword = "";
        String hashedPassword = "";

        when(passwordEncoder.encode(newPassword)).thenReturn(hashedPassword);  // ハッシュ化されたパスワードを返す
        when(usersMapper.updatePassword(mailAddress, hashedPassword)).thenReturn(0);

        boolean result = editPasswordService.editPassword(mailAddress, newPassword);

        assertFalse(result);
        verify(usersMapper).updatePassword(mailAddress, hashedPassword);
        verify(passwordEncoder).encode(newPassword);
    }

    @Test
    public void パスワードが正常に変更された場合() {
        String mailAddress = "user@example.com";
        String newPassword = "newPassword123";
        String hashedPassword = "$2a$10$7tO0BLGJdGQxWlnvYHkE8u5erJ.d8W99biAaFwJkMyZxMeJ0klSjq"; // モックのハッシュ化されたパスワード

        when(passwordEncoder.encode(newPassword)).thenReturn(hashedPassword);  // ハッシュ化されたパスワードを返す
        when(usersMapper.updatePassword(mailAddress, hashedPassword)).thenReturn(1); // 更新が成功した場合

        boolean result = editPasswordService.editPassword(mailAddress, newPassword);

        assertTrue(result);
        verify(usersMapper).updatePassword(mailAddress, hashedPassword);
        verify(passwordEncoder).encode(newPassword);
    }

    @Test
    public void パスワードの変更が失敗した場合() {
        String mailAddress = "user@example.com";
        String newPassword = "newPassword123";
        String hashedPassword = "$2a$10$7tO0BLGJdGQxWlnvYHkE8u5erJ.d8W99biAaFwJkMyZxMeJ0klSjq"; // モックのハッシュ化されたパスワード

        when(passwordEncoder.encode(newPassword)).thenReturn(hashedPassword);  // ハッシュ化されたパスワードを返す
        when(usersMapper.updatePassword(mailAddress, hashedPassword)).thenReturn(0); // 更新が失敗した場合

        boolean result = editPasswordService.editPassword(mailAddress, newPassword);

        assertFalse(result);
        verify(usersMapper).updatePassword(mailAddress, hashedPassword);
        verify(passwordEncoder).encode(newPassword);
    }
}
