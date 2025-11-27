package com.trademaxpro.controller;

import com.trademaxpro.model.PortfolioSummary;
import com.trademaxpro.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

// trading apis: buy, sell, view portfolio.
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/buy")
    public String buy(@RequestParam String userId, @RequestParam String ticker,@RequestParam int quantity) {
        portfolioService.buyStock(userId, ticker, quantity);
        return "Stock bought successfully";
    }

    @PostMapping("/sell")
    public String sell(@RequestParam String userId,@RequestParam String ticker,@RequestParam int quantity) {
        portfolioService.sellStock(userId, ticker, quantity);
        return "Stock sold successfully";
    }

    @GetMapping("/{userId}")
    public PortfolioSummary view(@PathVariable String userId) {
        return portfolioService.viewPortfolio(userId);
    }
}
