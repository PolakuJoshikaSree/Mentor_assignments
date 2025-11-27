package com.trademaxpro.controller;

import com.trademaxpro.model.PortfolioSummary;
import com.trademaxpro.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PortfolioController.class)
@ContextConfiguration(classes = {PortfolioController.class})
class PortfolioControllerTest {

    @Autowired MockMvc mvc;
    @MockBean PortfolioService portfolioService;

    @Test
    void buyStock() throws Exception {
        mvc.perform(post("/api/portfolio/buy?userId=1&ticker=TCS&quantity=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock bought successfully"));
    }

    @Test
    void portfolioView() throws Exception {
        PortfolioSummary summary = new PortfolioSummary(Collections.emptyList(),1000,1200,20);
        when(portfolioService.viewPortfolio("1")).thenReturn(summary);

        mvc.perform(get("/api/portfolio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalInvested").value(1000));
    }
}
