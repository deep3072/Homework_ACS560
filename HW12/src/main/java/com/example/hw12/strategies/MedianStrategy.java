package com.example.hw12.strategies;

import java.util.Collections;
import java.util.List;

public class MedianStrategy implements StockAnalysisStrategy {
    // Strategy to calculate the median of a list of numbers
    @Override
    public double analyze(List<Double> data) {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            return (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }
}