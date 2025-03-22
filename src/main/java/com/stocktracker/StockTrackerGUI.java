package com.stocktracker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockTrackerGUI extends JFrame {
    private JTextField stockSymbolField;
    private JTextArea resultArea;
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key

    public StockTrackerGUI() {
        setTitle("Stock Market Tracker");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        // Top Panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(30, 144, 255)); // Blue background

        JLabel label = new JLabel("Enter Stock Symbol:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(label);

        stockSymbolField = new JTextField(10);
        stockSymbolField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(stockSymbolField);

        JButton fetchButton = new JButton("Fetch Data");
        fetchButton.setFont(new Font("Arial", Font.BOLD, 14));
        fetchButton.setBackground(new Color(0, 150, 0)); // Green button
        fetchButton.setForeground(Color.WHITE);
        inputPanel.add(fetchButton);

        // Result Area
        resultArea = new JTextArea(10, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(240, 240, 240)); // Light gray
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button action
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stockSymbol = stockSymbolField.getText().trim();
                if (!stockSymbol.isEmpty()) {
                    fetchStockData(stockSymbol);
                } else {
                    resultArea.setText("Please enter a stock symbol.");
                }
            }
        });

        setVisible(true);
    }

    private void fetchStockData(String stockSymbol) {
        String urlString = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
                + stockSymbol + "&apikey=" + API_KEY;

        try {
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
            JsonObject globalQuote = jsonObject.getAsJsonObject("Global Quote");

            if (globalQuote != null) {
                String result = "Stock: " + stockSymbol + "\n" +
                        "Price: ₹" + globalQuote.get("05. price").getAsString() + "\n" +
                        "Open: ₹" + globalQuote.get("02. open").getAsString() + "\n" +
                        "High: ₹" + globalQuote.get("03. high").getAsString() + "\n" +
                        "Low: ₹" + globalQuote.get("04. low").getAsString() + "\n" +
                        "Change: " + globalQuote.get("10. change percent").getAsString();

                resultArea.setText(result);
            } else {
                resultArea.setText("No real-time stock data available.");
            }

        } catch (Exception e) {
            resultArea.setText("Error fetching stock data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StockTrackerGUI());
    }
}
