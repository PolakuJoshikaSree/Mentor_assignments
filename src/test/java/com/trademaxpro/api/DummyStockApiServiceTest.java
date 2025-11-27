package com.trademaxpro.api;

import com.trademaxpro.exception.StockNotFoundException;
import com.trademaxpro.model.StockQuote;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DummyStockApiServiceTest {

    DummyStockApiService service = new DummyStockApiService();

    @Test
    void getAllStocksReturnsList() {
        List<StockQuote> list = service.getAllStocks();
        assertFalse(list.isEmpty());
    }

    @Test
    void getValidStock() {
        StockQuote q = service.getStock("TCS");
        assertNotNull(q);
    }

    @Test
    void throwsWhenStockMissing() {
        assertThrows(StockNotFoundException.class, () -> service.getStock("XXXX"));
    }
}
