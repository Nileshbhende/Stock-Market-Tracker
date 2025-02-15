package com.stocktracker;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;
import java.util.Scanner;

public class StockFetcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Stock Symbol (e.g., AAPL for Apple, TSLA for Tesla): ");
        String symbol = scanner.nextLine().toUpperCase();

        try {
            Stock stock = YahooFinance.get(symbol);
            if (stock != null) {
                System.out.println("Stock: " + stock.getName());
                System.out.println("Current Price: $" + stock.getQuote().getPrice());
                System.out.println("Day High: $" + stock.getQuote().getDayHigh());
                System.out.println("Day Low: $" + stock.getQuote().getDayLow());
                System.out.println("Previous Close: $" + stock.getQuote().getPreviousClose());
            } else {
                System.out.println("Stock not found!");
            }
        } catch (IOException e) {
            System.out.println("Error fetching stock data: " + e.getMessage());
        }
        scanner.close();
    }
}
