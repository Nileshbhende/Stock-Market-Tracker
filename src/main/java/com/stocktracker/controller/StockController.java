package com.stocktracker.controller;

import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocktracker.service.StockService;

@RestController
@RequestMapping("/api")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock-price")
    public ResponseEntity<?> getStockPrice(@RequestParam String ticker) {
        try {
            BigDecimal price = stockService.getStockPrice(ticker);  // ✅ Fixed variable name
            return ResponseEntity.ok(Collections.singletonMap("price", price));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to fetch stock price: " + e.getMessage()));
        }
    }
}
