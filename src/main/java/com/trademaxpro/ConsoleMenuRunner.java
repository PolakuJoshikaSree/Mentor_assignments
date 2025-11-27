package com.trademaxpro;

import com.trademaxpro.model.User;
import com.trademaxpro.model.Wallet;
import com.trademaxpro.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Scanner;
@Profile("!test")  
@Component
public class ConsoleMenuRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ConsoleMenuRunner.class);

    private final UserService userService;
    private final WalletService walletService;
    private final StockService stockService;
    private final PortfolioService portfolioService;

    private static final String ASK_USER_ID = "Enter User ID: ";

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
            log.info("Choose: ");

            int x = sc.nextInt();

            try {
                if (x == 1) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Enter Name:");
                    String name = sc.next();

                    log.info("Enter Email:");
                    String email = sc.next();

                    log.info("Enter PAN:");
                    String pan = sc.next();

                    User registerUser = userService.registerUser(new User(id, name, email, pan, new Wallet(), new ArrayList<>()));

                    log.info("User registered successfully!");
                }

                else if (x == 2) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Enter Amount:");
                    double amount = sc.nextDouble();

                    log.info("Balance = {}", walletService.addMoney(id, amount));
                }

                else if (x == 3) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Enter Amount:");
                    double amount = sc.nextDouble();

                    log.info("Balance = {}", walletService.withdrawMoney(id, amount));
                }

                else if (x == 4) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Wallet Balance = {}", walletService.viewBalance(id));
                }

                else if (x == 5) {
                    stockService.getAllStocks().forEach(s ->
                            log.info("{} @ {}", s.getTicker(), s.getPrice())
                    );
                }

                else if (x == 6) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Enter Stock Ticker:");
                    String ticker = sc.next();

                    log.info("Enter Quantity:");
                    int qty = sc.nextInt();

                    portfolioService.buyStock(id, ticker, qty);
                    log.info("Stock purchased successfully!");
                }

                else if (x == 7) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();

                    log.info("Enter Stock Ticker:");
                    String ticker = sc.next();

                    log.info("Enter Quantity:");
                    int qty = sc.nextInt();

                    portfolioService.sellStock(id, ticker, qty);
                    log.info("Stock sold successfully!");
                }

                else if (x == 8) {
                    log.info(ASK_USER_ID);
                    String id = sc.next();
                    log.info("\n==========  USER PORTFOLIO REPORT  =========\n{}", 
                            portfolioService.prettyPortfolio(id));

                }

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
