package com.tabisketch.service;

import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IsExistMailAddressServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @Autowired
    private IIsExistMailAddressService isExistMailAddressService;

    @ParameterizedTest
    @MethodSource("sampleMailAddress")
    public void 動作するか(final String mailAddress) {
        when(this.usersMapper.isExistMailAddress(anyString())).thenReturn(1);

        assert this.isExistMailAddressService.execute(mailAddress);
        verify(this.usersMapper).isExistMailAddress(anyString());
    }

    private static Stream<String> sampleMailAddress() {
        final var mailAddress = "sample@example.com";
        return Stream.of(mailAddress);
    }
}