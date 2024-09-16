package com.example.hw3.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.hw3.models.StockData;
import com.example.hw3.repositories.StockRepository;
import com.example.hw3.services.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Override
    public List<StockData> getStockData() {
        List<StockData> stockDataList = StockRepository.getStockData();
        stockDataList.sort(createStockDataComparator());
        return stockDataList;
    }

    @Override
    public StockData getStockData(String date) {
        return StockRepository.getStockData(stock -> stock.getDate().equals(date)).stream().findFirst().orElse(null);
    }

    @Override
    public List<StockData> getStockData(String date, double open) {
        List<StockData> filteredStockData = new ArrayList<>(StockRepository.getStockData(stock -> stock.getDate().equals(date) && stock.getOpen() == open));
        if (filteredStockData.isEmpty()) {
            throw new NoSuchElementException();
        }
        filteredStockData.sort(createStockDataComparator());
        return filteredStockData;
    }

    private Comparator<StockData> createStockDataComparator() {
        return Comparator.comparing(StockData::getDate)
                .thenComparing(StockData::getOpen)
                .thenComparing(StockData::getHigh)
                .thenComparing(StockData::getLow)
                .thenComparing(StockData::getClose)
                .thenComparing(StockData::getAdjClose)
                .thenComparing(StockData::getVolume);
    }

    @Override
    public void add(StockData stockData) {
        StockRepository.addStockData(stockData);
    }

    @Override
    public void delete(StockData stockData) {
        StockRepository.deleteStockData(stockData);
    }

    @Override
    public void update(StockData stockData) {
        StockRepository.updateStockData(stockData);
    }
}