package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendEditPasswordForm;
import com.tabisketch.mapper.IPassWordAuthenticationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.SendEditPasswordService;  // 修正箇所
import jakarta.mail.MessagingException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import java.util.stream.Stream;

@SpringBootTest
public class SendEditPasswordServiceTest {

    @MockBean
    private IUsersMapper usersMapper;
    @MockBean
    private IPassWordAuthenticationTokensMapper passWordAuthenticationTokensMapper;
    @MockBean
    private SendEditPasswordService sendPasswordService;  // 追加

    @ParameterizedTest
    @MethodSource("動作するかのテストデータ")
    public void 動作するか(final SendEditPasswordForm sendEditPasswordForm) throws MessagingException {

        // モックの設定
        when(usersMapper.selectByPassword(sendEditPasswordForm.getCurrentPassword()))
                .thenReturn(User.generate("", ""));

        final var editPasswordService = new SendEditPasswordService(  // 修正箇所
                this.usersMapper,
                this.passWordAuthenticationTokensMapper,
                this.sendPasswordService
        );

        // 実行
        editPasswordService.execute(sendEditPasswordForm);

        // 検証
        verify(usersMapper).selectByPassword(any());
        verify(passWordAuthenticationTokensMapper).insertWithNewPassword(any());
        verify(sendPasswordService).execute(any());
    }

    // テストデータの提供
    private static Stream<SendEditPasswordForm> 動作するかのテストデータ() {
        final var e = new SendEditPasswordForm("sample@example.com", "password");
        return Stream.of(e);
    }
}
