package com.example.hw12.strategies;

import java.util.List;

public class AverageStrategy implements StockAnalysisStrategy {
    // Strategy to calculate the mean of a list of numbers
    @Override
    public double analyze(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }
}