package com.example.hw12.strategies;

import java.util.List;

public interface StockAnalysisStrategy {
    // Strategy interface to analyze stock data
    double analyze(List<Double> data);
}
