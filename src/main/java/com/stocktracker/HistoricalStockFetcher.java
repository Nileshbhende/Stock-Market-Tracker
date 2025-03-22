package com.stocktracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HistoricalStockFetcher {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter stock symbol (e.g., TCS.BSE): ");
        String stockSymbol = scanner.nextLine();
        scanner.close();

        String urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                + stockSymbol + "&apikey=" + API_KEY;

        try {
            // Make API request
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Parse JSON response
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject timeSeries = jsonObject.getAsJsonObject("Time Series (Daily)");

            if (timeSeries != null) {
                // Get the latest available date
                String latestDate = timeSeries.keySet().iterator().next();
                JsonObject latestData = timeSeries.getAsJsonObject(latestDate);

                System.out.println("\nStock: " + stockSymbol);
                System.out.println("Date: " + latestDate);
                System.out.println("Open: " + latestData.get("1. open").getAsString());
                System.out.println("High: " + latestData.get("2. high").getAsString());
                System.out.println("Low: " + latestData.get("3. low").getAsString());
                System.out.println("Close: " + latestData.get("4. close").getAsString());
                System.out.println("Volume: " + latestData.get("5. volume").getAsString());
            } else {
                System.out.println("No stock data available for " + stockSymbol);
            }

        } catch (Exception e) {
            System.out.println("Error fetching stock data: " + e.getMessage());
        }
    }
}
