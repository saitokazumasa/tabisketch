package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListPlanServiceTest {
    @MockBean
    private IUsersMapper usersMapper;
    @MockBean
    private IPlansMapper plansMapper;
    @Autowired
    private IListPlanService listPlanService;

    @ParameterizedTest
    @MethodSource("sampleMailAddress")
    public void 動作するか(final String mailAddress) {
        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(sampleUser());
        when(this.plansMapper.selectByUserId(anyInt())).thenReturn(new ArrayList<>());

        final var planList = this.listPlanService.execute(mailAddress);

        verify(this.usersMapper).selectByMailAddress(anyString());
        verify(this.plansMapper).selectByUserId(anyInt());
        assert planList != null;
    }

    private static User sampleUser() {
        return User.generate(
                "sample@example.com",
                "$2a$10$if7oiFZVmP9I59AOtzbSz.dWsdPUUuPTRkcIoR8iYhFpG/0COY.TO"
        );
    }

    private static Stream<String> sampleMailAddress() {
        final var mailAddress = "sample@example.com";
        return Stream.of(mailAddress);
    }
}
