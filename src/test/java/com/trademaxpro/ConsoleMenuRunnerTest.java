package com.trademaxpro;

import com.trademaxpro.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ConsoleMenuRunnerTest {

    @Mock
    UserService userService;
    @Mock
    WalletService walletService;
    @Mock
    StockService stockService;
    @Mock
    PortfolioService portfolioService;

    @InjectMocks
    ConsoleMenuRunner consoleRunner;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsoleMenuRunsAndExitsSafely() {

        // mock user input for "9 → Exit"
        InputStream input = new ByteArrayInputStream("9\n".getBytes());
        System.setIn(input);

        assertDoesNotThrow(() -> consoleRunner.run("dummy"));
    }

    @Test
    void testRegisterMenuOption() {
        InputStream input = new ByteArrayInputStream("1 test test mail pan 9\n".getBytes());
        System.setIn(input);

        assertDoesNotThrow(() -> consoleRunner.run("dummy"));

        verify(userService, times(1)).registerUser(any());
    }
}
