package com.example.hw13_aop.services;

import java.util.List;

import com.example.hw13_aop.models.StockData;

public interface StockService {
    StockData getStockData(String date);
    List<StockData> getStockData();
    List<StockData> getStockData(String date, double open);
    void add(StockData stockData);
    void delete(StockData stockData);
    void update(StockData stockData);
}