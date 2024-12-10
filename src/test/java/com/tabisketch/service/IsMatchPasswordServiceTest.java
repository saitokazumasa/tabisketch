package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.IsMatchPasswordForm;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IsMatchPasswordServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @Autowired
    private IIsMatchPasswordService isMatchPasswordService;

    @ParameterizedTest
    @MethodSource("sampleTrueIsMatchPasswordForm")
    public void 一致する時trueを返すか(final IsMatchPasswordForm isMatchPasswordForm) {
        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(sampleUser());

        assert this.isMatchPasswordService.execute(isMatchPasswordForm);
        verify(this.usersMapper).selectByMailAddress(anyString());
    }

    @ParameterizedTest
    @MethodSource("sampleFalseIsMatchPasswordForm")
    public void 一致しないときfalseを返すか(final IsMatchPasswordForm isMatchPasswordForm) {
        when(this.usersMapper.selectByMailAddress(any())).thenReturn(sampleUser());

        assert !this.isMatchPasswordService.execute(isMatchPasswordForm);
        verify(this.usersMapper).selectByMailAddress(isMatchPasswordForm.getMailAddress());
    }

    private static User sampleUser() {
        return User.generate(
                "sample@example.com",
                "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"
        );
    }

    private static Stream<IsMatchPasswordForm> sampleTrueIsMatchPasswordForm() {
        final var isMatchPasswordForm = new IsMatchPasswordForm("sample@example.com", "password");
        return Stream.of(isMatchPasswordForm);
    }

    private static Stream<IsMatchPasswordForm> sampleFalseIsMatchPasswordForm() {
        final var isMatchPasswordForm = new IsMatchPasswordForm("sample@example.com", "false-password");
        return Stream.of(isMatchPasswordForm);
    }
}
