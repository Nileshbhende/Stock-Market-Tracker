package com.stocktracker;
public class StockPrice {
    public static void main(String[] args) {
        // Sample stock price (no decimal)
        int price = 1405;  // Price as an integer without decimal
        
        // Calculate the percentage change (example)
        double currentPrice = 1405;  // Current price of the stock
        double previousClose = 1400;  // Previous closing price

        // Calculate the change percentage
        double change = ((currentPrice - previousClose) / previousClose) * 100;

        // Print stock info with integer price and change percentage
        System.out.println("Stock: TCS");
        System.out.println("Price: ₹" + price);  // Price without decimals
        System.out.println("Change: " + String.format("%.2f", change) + "%");  // Display change with two decimals
    }
}