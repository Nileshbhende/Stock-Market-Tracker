package com.stocktracker;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;
import java.math.BigDecimal;

public class StockFetcher {
    public static void fetchStockData(String stockSymbol) {
        try {
            Stock stock = YahooFinance.get(stockSymbol);
            if (stock != null) {
                BigDecimal price = stock.getQuote().getPrice();
                System.out.println("Yahoo Finance Stock Price of " + stockSymbol + ": ₹" + price);
            } else {
                System.out.println("Yahoo Finance Stock data not found!");
            }
        } catch (IOException e) {
            System.out.println("Error fetching Yahoo Finance stock data: " + e.getMessage());
        }
    }
}
