package com.example.hw12.services;

import java.util.List;
import java.util.Map;

import com.example.hw12.models.StockData;


public interface StockService {
    List<StockData> getAllStockData();
    List<StockData> getStockDataForMonthAndYear(int year, int month);
    Map<String, List<StockData>> getStockDataForYear(int year);
}
