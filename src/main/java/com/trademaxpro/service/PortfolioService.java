package com.trademaxpro.service;

import com.trademaxpro.exception.InsufficientFundsException;
import com.trademaxpro.exception.InsufficientHoldingsException;
import com.trademaxpro.exception.InvalidQuantityException;
import com.trademaxpro.exception.StockNotFoundException;
import com.trademaxpro.model.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    private final UserService userService;
    private final WalletService walletService;
    private final StockService stockService;

    public PortfolioService(UserService userService, WalletService walletService, StockService stockService) {
        this.userService = userService;
        this.walletService = walletService;
        this.stockService = stockService;
    }

    // 1. Check quantity > 0
    // 2. Get user + latest stock price from dummy API
    // 3. Calculate cost = price * qty
    // 4. If wallet has money → deduct & add to portfolio
    public void buyStock(String userId, String ticker, int quantity) {

        if (quantity <= 0)     // quantity validation
            throw new InvalidQuantityException("Quantity must be greater than zero");

        User user = userService.getUser(userId);            // fetch user
        StockQuote quote = stockService.getStock(ticker);   // live stock price

        double totalCost = quote.getPrice() * quantity;     // money required
        double balance = walletService.viewBalance(userId); // current wallet balance

        if (balance < totalCost)                            // insufficient funds
            throw new InsufficientFundsException("Not enough money to buy stock");

        walletService.withdrawMoney(userId, totalCost);     // deduct from wallet

        // Check if user already owns this stock
        PortfolioItem found = null;
        for (PortfolioItem item : user.getPortfolio()) {
            if (item.getTicker().equalsIgnoreCase(ticker)) {
                found = item; break;
            }
        }
        // If not owned , add new stock entry
        if (found == null) {
            user.getPortfolio().add(new PortfolioItem(ticker, quantity, quote.getPrice()));
        }
        // If already owned , increase quantity + recalculate average price
        else {
            found.addMore(quantity, quote.getPrice());
        }

        userService.updateUser(user);   // Save updated portfolio to DB,Without this, BUY does not persist
    }
    // 1. Check quantity
    // 2. Verify user owns the stock
    // 3. Check holding is enough to sell
    // 4. Get latest live price → calculate earnings
    // 5. Add money back to wallet & reduce quantity in portfolio
    public void sellStock(String userId, String ticker, int quantity) {

        if (quantity <= 0)
            throw new InvalidQuantityException("Quantity must be greater than zero");

        User user = userService.getUser(userId);

        PortfolioItem found = null;
        for (PortfolioItem item : user.getPortfolio()) {
            if (item.getTicker().equalsIgnoreCase(ticker)) {
                found = item; break;
            }
        }

        if (found == null)   // user doesn't own the stock
            throw new StockNotFoundException("You do not own this stock: " + ticker);

        if (found.getQuantity() < quantity) // not enough shares to sell
            throw new InsufficientHoldingsException("You do not have enough shares");

        StockQuote quote = stockService.getStock(ticker);   // live price again
        double earnings = quote.getPrice() * quantity;      // selling value

        found.sell(quantity);                               // reduce qty
        walletService.addMoney(userId, earnings);           // add back to wallet

        if (found.getQuantity() == 0)                       // remove entry if finished
            user.getPortfolio().remove(found);

        userService.updateUser(user);   // Persist portfolio after selling
    }


    // For every stock user owns:
    // Fetch live market price
    // Calculate invested vs current value
    // Calculate profit/loss %
    
    // Also returns:
    // Total invested amount
    // Total current value
    // Overall portfolio % return
    public PortfolioSummary viewPortfolio(String userId) {

        User user = userService.getUser(userId);

        List<PortfolioViewItem> items = new ArrayList<>();
        double totalInvested = 0;
        double totalCurrent = 0;

        for (PortfolioItem item : user.getPortfolio()) {

            StockQuote quote = stockService.getStock(item.getTicker()); // live price

            double invested = item.getAvgPrice() * item.getQuantity();  // original cost
            double current = quote.getPrice() * item.getQuantity();     // current value
            double plPercent = invested == 0 ? 0 : ((current - invested) / invested) * 100;

            totalInvested += invested;
            totalCurrent += current;

            items.add(new PortfolioViewItem(
                    item.getTicker(),
                    item.getQuantity(),
                    item.getAvgPrice(),
                    quote.getPrice(),
                    invested,
                    current,
                    plPercent
            ));
        }

        double overallReturn = (totalInvested == 0) ? 0 :
                ((totalCurrent - totalInvested) / totalInvested) * 100;

        return new PortfolioSummary(items, totalInvested, totalCurrent, overallReturn);
    }
    public String prettyPortfolio(String userId) {

        PortfolioSummary summary = viewPortfolio(userId);

        StringBuilder sb = new StringBuilder();
        sb.append("\n---------------- STOCK HOLDINGS ----------------\n");

        if (summary.getItems().isEmpty()) {
            sb.append("No stocks owned yet.\n");
        } else {
            for (PortfolioViewItem item : summary.getItems()) {
                sb.append("Stock : ").append(item.getTicker()).append("\n")
                  .append("Quantity : ").append(item.getQuantity()).append("\n")
                  .append("Buy Price : ₹").append(String.format("%.2f", item.getAvgBuyPrice())).append("\n")
                  .append("Current Price : ₹").append(String.format("%.2f", item.getCurrentPrice())).append("\n")
                  .append("Invested Value : ₹").append(String.format("%.2f", item.getInvestedValue())).append("\n")
                  .append("Current Value : ₹").append(String.format("%.2f", item.getCurrentValue())).append("\n")
                  .append("Profit/Loss % : ").append(String.format("%.2f", item.getProfitLossPercent())).append("%\n")
                  .append("--------------------------------------------------\n");
            }
        }

        sb.append("\n===== OVERALL SUMMARY =====\n")
          .append("Total Invested     : ₹").append(String.format("%.2f", summary.getTotalInvested())).append("\n")
          .append("Current Portfolio  : ₹").append(String.format("%.2f", summary.getTotalCurrent())).append("\n")
          .append("Overall Returns    : ").append(String.format("%.2f", summary.getOverallReturnPercent())).append("%\n")
          .append("==============================\n");

        return sb.toString();
    }

}
