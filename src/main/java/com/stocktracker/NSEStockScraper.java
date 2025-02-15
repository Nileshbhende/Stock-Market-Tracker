package com.stocktracker;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class NSEStockScraper {
    public static void fetchStockData(String stockSymbol) {
        String url = "https://www.nseindia.com/get-quotes/equity?symbol=" + stockSymbol;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements priceElements = doc.select(".equity_stock_data .price");
            if (!priceElements.isEmpty()) {
                String stockPrice = priceElements.first().text();
                System.out.println("NSE Stock Price of " + stockSymbol + ": ₹" + stockPrice);
            } else {
                System.out.println("NSE Stock price not found!");
            }
        } catch (IOException e) {
            System.out.println("Error fetching NSE stock data: " + e.getMessage());
        }
    }
}
