package com.trademaxpro;

import com.trademaxpro.model.User;
import com.trademaxpro.model.Wallet;
import com.trademaxpro.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Scanner;

@Component
public class ConsoleMenuRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConsoleMenuRunner.class);

    private static final String ASK_ID = "Enter User ID: ";
    private static final String ASK_AMOUNT = "Enter Amount: ";
    private static final String ASK_TICKER = "Enter Stock Ticker: ";
    private static final String ASK_QTY = "Enter Quantity: ";

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

            log.info("\n==== TradeMax Pro ====");
            log.info("1. Register User");
            log.info("2. Add Money");
            log.info("3. Withdraw Money");
            log.info("4. View Balance");
            log.info("5. View Stocks");
            log.info("6. Buy Stock");
            log.info("7. Sell Stock");
            log.info("8. View Portfolio");
            log.info("9. Exit");
            System.out.print("Choose: ");

            int x = sc.nextInt();

            try {

                /* ================= REGISTER USER ================= */
                if (x == 1) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Email: ");
                    String email = sc.next();
                    System.out.print("Enter PAN: ");
                    String pan = sc.next();

                    userService.registerUser(new User(id, name, email, pan, new Wallet(), new ArrayList<>()));
                    log.info("User registered successfully!");
                }

                /* ================= ADD MONEY ================= */
                else if (x == 2) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    System.out.print(ASK_AMOUNT);
                    double amt = sc.nextDouble();
                    log.info("Balance = {}", walletService.addMoney(id, amt));
                }

                /* ================= WITHDRAW MONEY ================= */
                else if (x == 3) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    System.out.print(ASK_AMOUNT);
                    double amt = sc.nextDouble();
                    log.info("Balance = {}", walletService.withdrawMoney(id, amt));
                }

                /* ================= VIEW BALANCE ================= */
                else if (x == 4) {
                    System.out.print(ASK_ID);
                    log.info("Wallet Balance = {}", walletService.viewBalance(sc.next()));
                }

                /* ================= VIEW STOCKS ================= */
                else if (x == 5) {
                    stockService.getAllStocks().forEach(s ->
                            log.info("{} @ {}", s.getTicker(), s.getPrice())
                    );
                }

                /* ================= BUY STOCK ================= */
                else if (x == 6) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    System.out.print(ASK_TICKER);
                    String tic = sc.next();
                    System.out.print(ASK_QTY);
                    int qty = sc.nextInt();

                    portfolioService.buyStock(id, tic, qty);
                    log.info("Stock purchased successfully!");
                }

                /* ================= SELL STOCK ================= */
                else if (x == 7) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    System.out.print(ASK_TICKER);
                    String tic = sc.next();
                    System.out.print(ASK_QTY);
                    int qty = sc.nextInt();

                    portfolioService.sellStock(id, tic, qty);
                    log.info("Stock sold successfully!");
                }

                /* ================= VIEW PORTFOLIO ================= */
                else if (x == 8) {
                    System.out.print(ASK_ID);
                    String id = sc.next();
                    log.info("\n========== USER PORTFOLIO REPORT ==========");
                    log.info("\n" + portfolioService.prettyPortfolio(id));
                    log.info("===========================================\n");
                }

                /* ================= EXIT ================= */
                else if (x == 9) {
                    log.info("Exiting... Goodbye!");
                    break;
                }

            } catch (Exception e) {
                log.error("ERROR: {}", e.getMessage());
            }
        }
    }
}
