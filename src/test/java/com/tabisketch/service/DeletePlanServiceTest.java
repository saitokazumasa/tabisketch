package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeletePlanServiceTest {
    @Autowired
    private IDeletePlanService deletePlanService;
    @MockitoBean
    private IPlansMapper plansMapper;
    @MockitoBean
    private IFindOnePlanWithUserService findOnePlanWithUserService;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        when(this.plansMapper.selectByUUID(any())).thenReturn(plan);
        when(this.findOnePlanWithUserService.execute(any(), anyString())).thenReturn(plan);
        when(this.plansMapper.delete(any())).thenReturn(1);

        final String uuid = plan.getUuid().toString();
        final String email = ExampleUser.gen().getEmail();
        this.deletePlanService.execute(uuid, email);

        verify(this.plansMapper).selectByUUID(any());
        verify(this.findOnePlanWithUserService).execute(any(), anyString());
        verify(this.plansMapper).delete(any());
    }
}
