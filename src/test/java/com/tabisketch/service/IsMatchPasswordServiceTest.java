package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.IsMatchPasswordForm;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.IsMatchPasswordService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IsMatchPasswordServiceTest {
    @MockBean
    private IUsersMapper usersMapper;

    @ParameterizedTest
    @MethodSource("一致する時trueを返すかのテストデータ")
    public void 一致する時trueを返すか(final IsMatchPasswordForm isMatchPasswordForm) {
        when(this.usersMapper.selectByMail(isMatchPasswordForm.getMail()))
                .thenReturn(User.generate(isMatchPasswordForm.getMail(), "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"));

        final var isMatchPasswordService = new IsMatchPasswordService(this.usersMapper);

        assert isMatchPasswordService.execute(isMatchPasswordForm);
        verify(this.usersMapper).selectByMail(isMatchPasswordForm.getMail());
    }

    private static Stream<IsMatchPasswordForm> 一致する時trueを返すかのテストデータ() {
        final var i = new IsMatchPasswordForm("sample@example.com", "password");
        return Stream.of(i);
    }

    @ParameterizedTest
    @MethodSource("一致しないときfalseを返すかのテストデータ")
    public void 一致しないときfalseを返すか(final IsMatchPasswordForm isMatchPasswordForm) {
        when(this.usersMapper.selectByMail(isMatchPasswordForm.getMail()))
                .thenReturn(User.generate(isMatchPasswordForm.getMail(), "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"));

        final var isMatchPasswordService = new IsMatchPasswordService(this.usersMapper);

        assert !isMatchPasswordService.execute(isMatchPasswordForm);
        verify(this.usersMapper).selectByMail(isMatchPasswordForm.getMail());
    }

    private static Stream<IsMatchPasswordForm> 一致しないときfalseを返すかのテストデータ() {
        final var i = new IsMatchPasswordForm("sample@example.com", "***");
        return Stream.of(i);
    }
}
