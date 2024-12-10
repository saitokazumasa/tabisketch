package com.tabisketch.controller;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.service.IListPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ListPlanController.class)
public class ListPlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IListPlanService listPlanService;

    @Test
    @WithMockUser(username = "sample@example.com")
    public void getが動作するか() throws Exception {
        when(listPlanService.execute(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/plan/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("planList"))
                .andExpect(MockMvcResultMatchers.model().attribute("planList", new ArrayList<Plan>()))
                .andExpect(MockMvcResultMatchers.view().name("plan/list"));
    }
}