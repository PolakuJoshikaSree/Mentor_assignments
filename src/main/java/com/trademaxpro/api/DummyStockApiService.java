package com.trademaxpro.api;

import com.trademaxpro.exception.StockNotFoundException;
import com.trademaxpro.model.StockQuote;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// fake external stock api.
@Service
public class DummyStockApiService {

    private Map<String, StockQuote> stockMap = new HashMap<>(); // base stocks

    public DummyStockApiService() {
        stockMap.put("TCS", new StockQuote("TCS", "Tata Consultancy Services", 3925.75));
        stockMap.put("INFY", new StockQuote("INFY", "Infosys Limited", 1540.40));
        stockMap.put("RELIANCE", new StockQuote("RELIANCE", "Reliance Industries", 2680.10));
    }

    public List<StockQuote> getAllStocks() {
        List<StockQuote> list = new ArrayList<>();

        for (StockQuote base : stockMap.values()) {
            list.add(applyRandomMove(base));
        }

        return list;
    }

    public StockQuote getStock(String ticker) {
        StockQuote base = stockMap.get(ticker);

        if (base == null) {
            throw new StockNotFoundException("Stock not found: " + ticker);
        }

        return applyRandomMove(base);
    }

    // small price movement to feel like live market.
    private StockQuote applyRandomMove(StockQuote base) {
        double change = (Math.random() - 0.5) * 20.0;
        double newPrice = base.getPrice() + change;
        return new StockQuote(base.getTicker(), base.getCompany(), newPrice);
    }
}
