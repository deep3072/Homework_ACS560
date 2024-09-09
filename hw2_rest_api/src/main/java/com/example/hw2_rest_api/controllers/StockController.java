package com.example.hw2_rest_api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hw2_rest_api.models.StockData;
import com.example.hw2_rest_api.services.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    // Endpoint to get stock data for all months and years
    @GetMapping("/all")
    public List<StockData> getAllStockData() {
        return stockService.getAllStockData();
    }

    // Endpoint to get stock data for a particular month and year
    @GetMapping("/{year}/{month}")
    public List<StockData> getStockDataForMonthAndYear(@PathVariable int year, @PathVariable int month) {
        return stockService.getStockDataForMonthAndYear(year, month);
    }

    // Endpoint to get stock data for a particular year
    @GetMapping("/{year}")
    public Map<String, List<StockData>> getStockDataForYear(@PathVariable int year) {
        return stockService.getStockDataForYear(year);
    }
}