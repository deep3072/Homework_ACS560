package com.example.hw12.strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeStrategy implements StockAnalysisStrategy {
    // Strategy to calculate the mode of a list of numbers
    @Override
    public double analyze(List<Double> data) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double num : data) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        double mode = data.get(0);
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }
}