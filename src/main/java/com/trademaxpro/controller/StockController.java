package com.trademaxpro.controller;

import com.trademaxpro.model.StockQuote;
import com.trademaxpro.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// stock apis: list projected and also gets price.
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<StockQuote> getAll() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{ticker}")
    public StockQuote getOne(@PathVariable String ticker) {
        return stockService.getStock(ticker);
    }
}
