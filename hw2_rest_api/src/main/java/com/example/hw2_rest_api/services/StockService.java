package com.example.hw2_rest_api.services;

import java.util.List;
import java.util.Map;

import com.example.hw2_rest_api.models.StockData;

public interface StockService {
    List<StockData> getAllStockData();
    List<StockData> getStockDataForMonthAndYear(int year, int month);
    Map<String, List<StockData>> getStockDataForYear(int year);
}