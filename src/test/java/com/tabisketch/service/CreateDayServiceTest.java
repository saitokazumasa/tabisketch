package com.tabisketch.service;

import com.tabisketch.bean.form.CreateDayForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IDaysMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateDayServiceTest {
    @Autowired
    private ICreateDayService createDayService;
    @MockitoBean
    private IDaysMapper daysMapper;

    @Test
    public void testExecute() throws InsertFailedException {
        when(this.daysMapper.insert(any())).thenReturn(1);

        final var createDayForm = new CreateDayForm(
                1,
                1,
                0,
                "0000",
                true,
                true
        );
        this.createDayService.execute(createDayForm);

        verify(this.daysMapper).insert(any());
    }
}
