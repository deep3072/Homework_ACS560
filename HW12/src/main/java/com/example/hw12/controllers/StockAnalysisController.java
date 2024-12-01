package com.example.hw12.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hw12.services.StockAnalysisService;


@RestController
@RequestMapping("/api/stocks")
public class StockAnalysisController {
    
    @Autowired
    private StockAnalysisService stockAnalysisService;

    // Endpoint to analyze stock data for a particular month and year
    @GetMapping("/analyze/{year}/{month}")
    public Map<String, Double> analyzeStockData(
            @PathVariable int year, 
            @PathVariable int month) {
                
        System.out.println("Received request for year: " + year + " and month: " + month);

        return stockAnalysisService.analyzeStockForMonthandYear(year, month);
    }

    // Endpoint to analyze stock data for a every month in a particular year
    @GetMapping("/analyze/{year}")
    public Map<String, Map<String, Double>> analyzeStockDataForYear(@PathVariable int year) {
        System.out.println("Received request for year: " + year);

        return stockAnalysisService.analyzeStockForYear(year);
    }

    // Endpoint to analyze stock data for all months and years
    @GetMapping("/analyze/all")
    public Map<String, Map<String, Map<String, Double>>> analyzeAllStockData() {
        System.out.println("Received request for all stock data");

        return stockAnalysisService.analyzeAllStockData();
    }
}
