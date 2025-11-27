package com.trademaxpro.controller;

import com.trademaxpro.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
@ContextConfiguration(classes = {WalletController.class})
class WalletControllerTest {

    @Autowired MockMvc mvc;
    @MockBean WalletService walletService;

    @Test
    void addMoney() throws Exception {
        when(walletService.addMoney("1",1000)).thenReturn(1000.0);

        mvc.perform(post("/api/wallet/add?userId=1&amount=1000"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.0"));
    }

    @Test
    void viewBalance() throws Exception {
        when(walletService.viewBalance("1")).thenReturn(2000.0);

        mvc.perform(get("/api/wallet/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("2000.0"));
    }
}
