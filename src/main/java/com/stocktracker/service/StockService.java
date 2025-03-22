package com.stocktracker.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class StockService {

    @Value("${alpha.vantage.api.key}") // API key from application.properties
    private String apiKey;

    public BigDecimal getStockPrice(String symbol) {
        try {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject globalQuote = jsonResponse.getAsJsonObject("Global Quote");

            if (globalQuote == null || !globalQuote.has("05. price")) {
                throw new RuntimeException("Invalid stock data received");
            }

            return new BigDecimal(globalQuote.get("05. price").getAsString());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching stock data: " + e.getMessage());
        }
    }
}
