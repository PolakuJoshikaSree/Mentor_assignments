package com.trademaxpro;

import com.trademaxpro.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import static org.mockito.Mockito.*;

class ConsoleMenuRunnerTest {

    @Test
    void test_all_menu_options_1_to_8() {

        UserService userService = mock(UserService.class);
        WalletService walletService = mock(WalletService.class);
        StockService stockService = mock(StockService.class);
        PortfolioService portfolioService = mock(PortfolioService.class);

        // required default return mocks
        when(walletService.addMoney("U1",1000)).thenReturn(1000.0);
        when(walletService.withdrawMoney("U1",500)).thenReturn(500.0);
        when(walletService.viewBalance("U1")).thenReturn(500.0);
        when(stockService.getAllStocks()).thenReturn(Collections.emptyList());
        when(portfolioService.prettyPortfolio("U1")).thenReturn("PORTFOLIO OK");

        String input = String.join("\n",
                "1","U1","Josh","j@j.com","ABCDE1234",   // register
                "2","U1","1000",                        // add money
                "3","U1","500",                         // withdraw
                "4","U1",                                // balance
                "5",                                     // view stocks
                "6","U1","TCS","4",                      // buy stock
                "7","U1","TCS","2",                      // sell stock
                "8","U1",                                // view portfolio
                "9"                                      // exit
        ) + "\n";

        InputStream sys = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // run
        ConsoleMenuRunner runner = new ConsoleMenuRunner(
                userService, walletService, stockService, portfolioService
        );
        runner.run();

        System.setIn(sys);

        // Verify Calls
        verify(userService, times(1)).registerUser(Mockito.any());
        verify(walletService, times(1)).addMoney("U1",1000);
        verify(walletService, times(1)).withdrawMoney("U1",500);
        verify(walletService, times(1)).viewBalance("U1");
        verify(stockService, times(1)).getAllStocks();
        verify(portfolioService, times(1)).buyStock("U1","TCS",4);
        verify(portfolioService, times(1)).sellStock("U1","TCS",2);
        verify(portfolioService, times(1)).prettyPortfolio("U1");
    }
}
