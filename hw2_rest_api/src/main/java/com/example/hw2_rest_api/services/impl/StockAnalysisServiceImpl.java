package com.example.hw2_rest_api.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hw2_rest_api.models.StockData;
import com.example.hw2_rest_api.repositories.StockRepository;
import com.example.hw2_rest_api.services.StockAnalysisService;

@Service
public class StockAnalysisServiceImpl implements StockAnalysisService {

    @Autowired
    private StockRepository stockRepository;

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

        // System.out.println("Monthly data size: " + monthlyData.size());

        if (monthlyData.isEmpty()) {
            return Collections.emptyMap();
        }
        // System.out.println("Data found for the given month and year.");

        double mean = calculateMean(monthlyData);
        double median = calculateMedian(monthlyData);
        double mode = calculateMode(monthlyData);
        double average = calculateAverage(monthlyData);

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
            monthlyAnalysis.put("Mean", calculateMean(entry.getValue()));
            monthlyAnalysis.put("Median", calculateMedian(entry.getValue()));
            monthlyAnalysis.put("Mode", calculateMode(entry.getValue()));
            monthlyAnalysis.put("Average", calculateAverage(entry.getValue()));
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
                monthlyAnalysis.put("Mean", calculateMean(monthEntry.getValue()));
                monthlyAnalysis.put("Median", calculateMedian(monthEntry.getValue()));
                monthlyAnalysis.put("Mode", calculateMode(monthEntry.getValue()));
                monthlyAnalysis.put("Average", calculateAverage(monthEntry.getValue()));
                yearAnalysis.put(monthEntry.getKey(), monthlyAnalysis);
            }
            analysisResults.put(yearEntry.getKey(), yearAnalysis);
        }

        return analysisResults;
    }

    private double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    private double calculateMedian(List<Double> data) {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            return (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }

    private double calculateMode(List<Double> data) {
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
    private double calculateAverage(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }
}
