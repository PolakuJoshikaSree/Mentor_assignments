package com.trademaxpro.service;

import com.trademaxpro.api.DummyStockApiService;
import com.trademaxpro.model.StockQuote;
import org.springframework.stereotype.Service;
import java.util.List;

// this service just passes requests to DummyStockApiService
@Service
public class StockService {

    private final DummyStockApiService apiService;

    public StockService(DummyStockApiService apiService) {
        this.apiService = apiService;
    }

    // get list of all stocks with updated prices
    public List<StockQuote> getAllStocks() {
        return apiService.getAllStocks();
    }

    // get price of one stock
    public StockQuote getStock(String ticker) {
        return apiService.getStock(ticker);
    }
}
