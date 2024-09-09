package com.example.hw2_rest_api.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hw2_rest_api.models.StockData;
import com.example.hw2_rest_api.repositories.StockRepository;
import com.example.hw2_rest_api.services.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<StockData> getAllStockData() {
        return stockRepository.loadStockDataFromCSV();
    }

    @Override
    public List<StockData> getStockDataForMonthAndYear(int year, int month) {
        List<StockData> stockDataList = stockRepository.loadStockDataFromCSV();
        List<StockData> monthandYearData = new ArrayList<>();

        // Traverse the list and get the whole row for the specified month and year
        for (StockData stock : stockDataList) {
            String[] dateParts = stock.getDate().split("-");
            int dataYear = Integer.parseInt(dateParts[0]);
            int dataMonth = Integer.parseInt(dateParts[1]);

            if (dataYear == year && dataMonth == month) {
                monthandYearData.add(stock);
            }
        }

            return monthandYearData;
    }

    @Override
    public Map<String, List<StockData>> getStockDataForYear(int year) {
        List<StockData> stockDataList = stockRepository.loadStockDataFromCSV();
        Map<String, List<StockData>> yearlyData = new HashMap<>();
    
        // Traverse the list and get the whole row for each month of the specified year
        for (StockData stock : stockDataList) {
            String[] dateParts = stock.getDate().split("-");
            int dataYear = Integer.parseInt(dateParts[0]);
            int dataMonth = Integer.parseInt(dateParts[1]);
    
            if (dataYear == year) {
                String monthKey = String.format("%02d", dataMonth);
                yearlyData.computeIfAbsent(monthKey, k -> new ArrayList<>()).add(stock);
            }
        }
    
        return yearlyData;
    }}