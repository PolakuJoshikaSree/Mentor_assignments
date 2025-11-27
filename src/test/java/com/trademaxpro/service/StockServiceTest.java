package com.trademaxpro.service;

import com.trademaxpro.api.DummyStockApiService;
import com.trademaxpro.model.StockQuote;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Test
    void getAllStocks_returnsList() {
        DummyStockApiService api = mock(DummyStockApiService.class);
        when(api.getAllStocks()).thenReturn(List.of(
                new StockQuote("TCS", "TCS", 100.0)
        ));

        StockService stockService = new StockService(api);

        List<StockQuote> list = stockService.getAllStocks();

        assertFalse(list.isEmpty());
    }
}
