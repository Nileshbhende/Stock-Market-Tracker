package com.stocktracker.service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;

public class TestYahooFinance {
    public static void main(String[] args) {
        try {
            Stock stock = YahooFinance.get("TCS.NS");  // Use NSE/BSE symbol
            BigDecimal price = stock.getQuote().getPrice();
            System.out.println("Stock Price: ₹" + price);
        } catch (IOException e) {
            System.err.println("Error fetching stock price: " + e.getMessage());
        }
    }
}
