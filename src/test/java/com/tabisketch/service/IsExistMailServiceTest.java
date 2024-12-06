package com.tabisketch.service;

import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.IsExistMailService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IsExistMailServiceTest {
    @MockBean
    private IUsersMapper usersMapper;

    @ParameterizedTest
    @ValueSource(strings = {"sample@example.com"})
    public void 動作するか(final String mail) {
        when(this.usersMapper.isExistMail(any())).thenReturn(1);

        final var isExistMailService = new IsExistMailService(this.usersMapper);
        assert isExistMailService.execute(mail);
    }
}
