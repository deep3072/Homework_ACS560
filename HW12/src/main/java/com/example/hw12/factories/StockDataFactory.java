package com.example.hw12.factories;

import com.example.hw12.models.StockData;

// Factory class
public class StockDataFactory {
    public static StockData createStockData(String[] csvData) {
        
        String date = csvData[0];
        double open = Double.parseDouble(csvData[1]);
        double high = Double.parseDouble(csvData[2]);
        double low = Double.parseDouble(csvData[3]);
        double close = Double.parseDouble(csvData[4]);
        return new StockData(date, open, high, low, close);
    }
}