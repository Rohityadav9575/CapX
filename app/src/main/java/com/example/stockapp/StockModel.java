package com.example.stockapp;

import java.util.Map;

public class StockModel {
    private String symbol;
    private String lastRefreshed;
    private double currentPrice;
    private double changePercentage;

    // Constructor
    public StockModel(String symbol, String lastRefreshed, double currentPrice, double changePercentage) {
        this.symbol = symbol;
        this.lastRefreshed = lastRefreshed;
        this.currentPrice = currentPrice;
        this.changePercentage = changePercentage;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getPriceChangePercentage() {
        return changePercentage; // Fixed variable name to match the field
    }

    // Method to create a StockModel from API response
    public static StockModel fromApiResponse(String symbol, String lastRefreshed, double currentPrice, double previousPrice) {
        double priceChangePercentage = calculatePriceChange(currentPrice, previousPrice);
        return new StockModel(symbol, lastRefreshed, currentPrice, priceChangePercentage);
    }

    // Method to calculate price change percentage
    private static double calculatePriceChange(double currentPrice, double previousPrice) {
        // Prevent division by zero
        if (previousPrice == 0) {
            return 0.0;
        }
        // Calculate the percentage change
        return ((currentPrice - previousPrice) / previousPrice) * 100;
    }
}
