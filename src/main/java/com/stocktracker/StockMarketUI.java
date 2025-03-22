package com.stocktracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockMarketUI {

    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("Stock Market Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1));

        // Input field for stock symbol
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter NSE Stock Symbol:");
        JTextField stockInput = new JTextField(10);
        JButton fetchButton = new JButton("Fetch Price");
        panel.add(label);
        panel.add(stockInput);
        panel.add(fetchButton);
        frame.add(panel);

        // Output label
        JLabel resultLabel = new JLabel("Stock Price: ₹ --", SwingConstants.CENTER);
        frame.add(resultLabel);

        // Button Click Action
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stockSymbol = stockInput.getText().toUpperCase();
                if (!stockSymbol.isEmpty()) {
                    String price = YahooStockFetcher.getStockPrice(stockSymbol);
                    resultLabel.setText("Stock Price: ₹" + price);
                } else {
                    resultLabel.setText("Please enter a stock symbol.");
                }
            }
        });

        // Show UI
        frame.setVisible(true);
    }
}
