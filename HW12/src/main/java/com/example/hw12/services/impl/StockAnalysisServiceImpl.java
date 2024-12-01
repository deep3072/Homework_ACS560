package com.example.hw12.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hw12.models.StockData;
import com.example.hw12.repositories.StockRepository;
import com.example.hw12.services.StockAnalysisService;
import com.example.hw12.strategies.AverageStrategy;
import com.example.hw12.strategies.MeanStrategy;
import com.example.hw12.strategies.MedianStrategy;
import com.example.hw12.strategies.ModeStrategy;
import com.example.hw12.strategies.StockAnalysisStrategy;

@Service
public class StockAnalysisServiceImpl implements StockAnalysisService {
    // Context class

    @Autowired
    private StockRepository stockRepository;

    private StockAnalysisStrategy meanStrategy = new MeanStrategy();
    private StockAnalysisStrategy medianStrategy = new MedianStrategy();
    private StockAnalysisStrategy modeStrategy = new ModeStrategy();
    private StockAnalysisStrategy averageStrategy = new AverageStrategy();

    @Override
    public Map<String, Double> analyzeStockForMonthandYear(int year, int month) {
        List<StockData> stockDataList = stockRepository.loadStockDataFromCSV();

        List<Double> monthlyData = new ArrayList<>();
        // System.out.println("Analyzing stock data for year: " + year + " and month: " + month);
        // System.out.println("Stock data size: " + stockDataList.size());

        // print stockDataList 10 rows
        for (int i = 0; i < 10; i++) {
            System.out.println(stockDataList.get(i).toString());    
        }

        for (StockData stock : stockDataList) {
            String[] dateParts = stock.getDate().split("-");
            int dataYear = Integer.parseInt(dateParts[0]);
            int dataMonth = Integer.parseInt(dateParts[1]);

            if (dataYear == year && dataMonth == month) {
                monthlyData.add(stock.getClose());
            }
        }

        if (monthlyData.isEmpty()) {
            return Collections.emptyMap();
        }

        double mean = meanStrategy.analyze(monthlyData);
        double median = medianStrategy.analyze(monthlyData);
        double mode = modeStrategy.analyze(monthlyData);
        double average = averageStrategy.analyze(monthlyData);

        Map<String, Double> analysisResults = new HashMap<>();
        analysisResults.put("Mean", mean);
        analysisResults.put("Median", median);
        analysisResults.put("Mode", mode);
        analysisResults.put("Average", average);
        // System.out.println("Mean: " + mean + ", Median: " + median + ", Mode: " + mode);

        return analysisResults;
    }

    @Override
    public Map<String, Map<String, Double>> analyzeStockForYear(int year) {
        List<StockData> stockDataList = stockRepository.loadStockDataFromCSV();
        Map<String, List<Double>> yearlyData = new HashMap<>();

        for (StockData stock : stockDataList) {
            String[] dateParts = stock.getDate().split("-");
            int dataYear = Integer.parseInt(dateParts[0]);
            int dataMonth = Integer.parseInt(dateParts[1]);

            if (dataYear == year) {
                String monthKey = String.format("%02d", dataMonth);
                yearlyData.computeIfAbsent(monthKey, k -> new ArrayList<>()).add(stock.getClose());
            }
        }

        Map<String, Map<String, Double>> analysisResults = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : yearlyData.entrySet()) {
            Map<String, Double> monthlyAnalysis = new HashMap<>();
            monthlyAnalysis.put("Mean", meanStrategy.analyze(entry.getValue()));
            monthlyAnalysis.put("Median", medianStrategy.analyze(entry.getValue()));
            monthlyAnalysis.put("Mode", modeStrategy.analyze(entry.getValue()));
            monthlyAnalysis.put("Average", averageStrategy.analyze(entry.getValue()));
            analysisResults.put(entry.getKey(), monthlyAnalysis);
        }

        return analysisResults;
    }

    @Override
    public Map<String, Map<String, Map<String, Double>>> analyzeAllStockData() {
        List<StockData> stockDataList = stockRepository.loadStockDataFromCSV();
        Map<String, Map<String, List<Double>>> allData = new HashMap<>();

        for (StockData stock : stockDataList) {
            String[] dateParts = stock.getDate().split("-");
            int dataYear = Integer.parseInt(dateParts[0]);
            int dataMonth = Integer.parseInt(dateParts[1]);

            String yearKey = String.valueOf(dataYear);
            String monthKey = String.format("%02d", dataMonth);

            allData.computeIfAbsent(yearKey, k -> new HashMap<>())
                   .computeIfAbsent(monthKey, k -> new ArrayList<>())
                   .add(stock.getClose());
        }

        Map<String, Map<String, Map<String, Double>>> analysisResults = new HashMap<>();
        for (Map.Entry<String, Map<String, List<Double>>> yearEntry : allData.entrySet()) {
            Map<String, Map<String, Double>> yearAnalysis = new HashMap<>();
            for (Map.Entry<String, List<Double>> monthEntry : yearEntry.getValue().entrySet()) {
                Map<String, Double> monthlyAnalysis = new HashMap<>();
                monthlyAnalysis.put("Mean", meanStrategy.analyze(monthEntry.getValue()));
                monthlyAnalysis.put("Median", medianStrategy.analyze(monthEntry.getValue()));
                monthlyAnalysis.put("Mode", modeStrategy.analyze(monthEntry.getValue()));
                monthlyAnalysis.put("Average", averageStrategy.analyze(monthEntry.getValue()));
                yearAnalysis.put(monthEntry.getKey(), monthlyAnalysis);
            }
            analysisResults.put(yearEntry.getKey(), yearAnalysis);
        }

        return analysisResults;
    }
}
