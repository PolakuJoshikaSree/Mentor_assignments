package com.trademaxpro.service;

import com.trademaxpro.exception.*;
import com.trademaxpro.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PortfolioServiceTest {

    private UserService userService;
    private WalletService walletService;
    private StockService stockService;
    private PortfolioService portfolioService;

    private User user;

    @BeforeEach
    void setup() {
        userService = mock(UserService.class);
        walletService = mock(WalletService.class);
        stockService = mock(StockService.class);
        portfolioService = new PortfolioService(userService, walletService, stockService);

        // sample user
        user = new User();
        user.setId("u1");
        user.setPortfolio(new ArrayList<>());
        user.setWallet(new Wallet());
    }

    @Test
    void buyStock_successfullyAddsNewStock() {
        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS"))
                .thenReturn(new StockQuote("TCS", "TCS", 2000));

        when(walletService.viewBalance("u1")).thenReturn(10000.0);

        portfolioService.buyStock("u1", "TCS", 2);

        assertEquals(1, user.getPortfolio().size());
        assertEquals(2, user.getPortfolio().get(0).getQuantity());
    }

    @Test
    void buyStock_updatesExistingHolding() {
        user.getPortfolio()
                .add(new PortfolioItem("TCS", 2, 2000));

        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS")).thenReturn(new StockQuote("TCS", "TCS", 2200));
        when(walletService.viewBalance("u1")).thenReturn(10000.0);

        portfolioService.buyStock("u1", "TCS", 1);

        PortfolioItem item = user.getPortfolio().get(0);

        assertEquals(3, item.getQuantity());
        assertTrue(item.getAvgPrice() >= 2000 && item.getAvgPrice() <= 2200);
    }

    @Test
    void buyStock_throwsWhenLowBalance() {
        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS")).thenReturn(new StockQuote("TCS", "TCS", 6000));
        when(walletService.viewBalance("u1")).thenReturn(4000.0);

        assertThrows(InsufficientFundsException.class,
                () -> portfolioService.buyStock("u1", "TCS", 1));
    }

    @Test
    void sellStock_success() {
        user.getPortfolio().add(new PortfolioItem("TCS", 3, 1500));

        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS")).thenReturn(new StockQuote("TCS", "TCS", 2000));

        portfolioService.sellStock("u1", "TCS", 2);

        assertEquals(1, user.getPortfolio().get(0).getQuantity());
    }

    @Test
    void sellStock_removesItemWhenZeroQuantity() {
        user.getPortfolio().add(new PortfolioItem("TCS", 1, 1800));

        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS")).thenReturn(new StockQuote("TCS", "TCS", 2000));

        portfolioService.sellStock("u1", "TCS", 1);

        assertEquals(0, user.getPortfolio().size());
    }

    @Test
    void sellStock_throwsWhenNotOwned() {
        when(userService.getUser("u1")).thenReturn(user);

        assertThrows(StockNotFoundException.class,
                () -> portfolioService.sellStock("u1", "TCS", 1));
    }

    @Test
    void viewPortfolio_returnsCorrectSummary() {
        user.getPortfolio().add(new PortfolioItem("TCS", 2, 1000));
        when(userService.getUser("u1")).thenReturn(user);
        when(stockService.getStock("TCS"))
                .thenReturn(new StockQuote("TCS", "TCS", 1500));

        PortfolioSummary summary = portfolioService.viewPortfolio("u1");

        assertEquals(1, summary.getItems().size());
        assertEquals(2000, summary.getTotalInvested());
        assertEquals(3000, summary.getTotalCurrent());
        assertTrue(summary.getOverallReturnPercent() > 0);
    }
}
