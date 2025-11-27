package com.trademaxpro.controller;

import com.trademaxpro.model.StockQuote;
import com.trademaxpro.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
@ContextConfiguration(classes = {StockController.class})
class StockControllerTest {

    @Autowired MockMvc mvc;
    @MockBean StockService stockService;

    @Test
    void getAllStocks() throws Exception {
        when(stockService.getAllStocks()).thenReturn(List.of(new StockQuote("TCS",null,3000)));

        mvc.perform(get("/api/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("TCS"));
    }

    @Test
    void getStock() throws Exception {
        when(stockService.getStock("TCS")).thenReturn(new StockQuote("TCS",null,3000));

        mvc.perform(get("/api/stocks/TCS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(3000.0));
    }
}
