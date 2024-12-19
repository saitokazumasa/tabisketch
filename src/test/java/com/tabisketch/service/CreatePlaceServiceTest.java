package com.tabisketch.service;

import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IPlacesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreatePlaceServiceTest {
    @Autowired
    private ICreatePlaceService createPlaceService;
    @MockitoBean
    private IPlacesMapper placesMapper;

    @Test
    public void testExecute() throws InsertFailedException {
        when(this.placesMapper.insert(any())).thenReturn(1);

        final var createPlaceForm = new CreatePlaceForm(
                1,
                1,
                0,
                LocalTime.of(10, 0),
                LocalTime.of(11,0),
                null,
                null,
                null,
                null,
                null
        );

        this.createPlaceService.execute(createPlaceForm);
    }
}
