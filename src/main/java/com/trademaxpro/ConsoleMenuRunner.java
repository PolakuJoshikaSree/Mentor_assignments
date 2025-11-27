package com.trademaxpro;

import com.trademaxpro.model.User;
import com.trademaxpro.model.Wallet;
import com.trademaxpro.service.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class ConsoleMenuRunner implements CommandLineRunner {

    private final UserService userService;
    private final WalletService walletService;
    private final StockService stockService;
    private final PortfolioService portfolioService;

    public ConsoleMenuRunner(UserService userService, WalletService walletService,
                             StockService stockService, PortfolioService portfolioService) {
        this.userService = userService;
        this.walletService = walletService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
    }

    @Override
    public void run(String... args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n==== TradeMax Pro ====");
            System.out.println("1. Register User");
            System.out.println("2. Add Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Balance");
            System.out.println("5. View Stocks");
            System.out.println("6. Buy Stock");
            System.out.println("7. Sell Stock");
            System.out.println("8. View Portfolio");
            System.out.println("9. Exit");
            System.out.print("Choose: ");

            int x = sc.nextInt();

            try {

                /* ================= REGISTER USER ================= */
                if (x == 1) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.print("Enter Name: ");
                    String name = sc.next();

                    System.out.print("Enter Email: ");
                    String email = sc.next();

                    System.out.print("Enter PAN: ");
                    String pan = sc.next();

                    userService.registerUser(new User(id, name, email, pan, new Wallet(), new ArrayList<>()));
                    System.out.println("User registered successfully!");
                }

                /* ================= ADD MONEY ================= */
                else if (x == 2) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    System.out.println("Balance = " + walletService.addMoney(id, amount));
                }

                /* ================= WITHDRAW MONEY ================= */
                else if (x == 3) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    System.out.println("Balance = " + walletService.withdrawMoney(id, amount));
                }

                /* ================= VIEW BALANCE ================= */
                else if (x == 4) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.println("Wallet Balance = " + walletService.viewBalance(id));
                }

                /* ================= VIEW STOCKS ================= */
                else if (x == 5) {
                    stockService.getAllStocks().forEach(s ->
                            System.out.println(s.getTicker() + " @ " + s.getPrice()));
                }

                /* ================= BUY STOCK ================= */
                else if (x == 6) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.print("Enter Stock Ticker: ");
                    String ticker = sc.next();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    portfolioService.buyStock(id, ticker, qty);
                    System.out.println("Stock purchased successfully!");
                }

                /* ================= SELL STOCK ================= */
                else if (x == 7) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.print("Enter Stock Ticker: ");
                    String ticker = sc.next();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    portfolioService.sellStock(id, ticker, qty);
                    System.out.println("Stock sold successfully!");
                }

                /* ================= VIEW PORTFOLIO ================= */
                else if (x == 8) {
                    System.out.print("Enter User ID: ");
                    String id = sc.next();

                    System.out.println("\n==========  USER PORTFOLIO REPORT  ==========");
                    System.out.println(portfolioService.prettyPortfolio(id));
                    System.out.println("==================================================");
                }


                /* ================= EXIT ================= */
                else if (x == 9) {
                    System.out.println("Exiting... Goodbye!");
                    break;
                }

            } catch (Exception e) {
                System.out.println("ERROR : " + e.getMessage());
            }
        }
    }
}
