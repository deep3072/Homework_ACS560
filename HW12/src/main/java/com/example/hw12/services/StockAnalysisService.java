package com.example.hw12.services;
import java.util.Map;

public interface StockAnalysisService {
    Map<String, Double> analyzeStockForMonthandYear(int year, int month);
    Map<String, Map<String, Double>> analyzeStockForYear(int year);
    Map<String, Map<String, Map<String, Double>>> analyzeAllStockData();
}