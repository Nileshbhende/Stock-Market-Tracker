package com.stocktracker; // Ensure this matches your project structure

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class YahooStockFetcher {
    
    public static String getStockPrice(String stockSymbol) {
        String url = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=" + stockSymbol + ".NS";

        try {
            // Set up connection with proper headers
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .header("Accept", "application/json")
                    .timeout(10000)
                    .execute();

            // Parse response as JSON
            String jsonResponse = response.body();
            if (jsonResponse.contains("\"regularMarketPrice\":")) {
                return jsonResponse.split("\"regularMarketPrice\":")[1].split(",")[0];
            } else {
                return "Error: Could not retrieve stock price.";
            }
        } catch (IOException e) {
            return "Error fetching stock data: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        String stockSymbol = "TCS";
        System.out.println(stockSymbol + " Current Price: ₹" + getStockPrice(stockSymbol));
    }
}
