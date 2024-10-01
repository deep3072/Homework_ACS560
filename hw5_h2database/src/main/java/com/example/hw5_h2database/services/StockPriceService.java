package com.example.hw5_h2database.services;

import com.example.hw5_h2database.entities.StockPrice;
import com.example.hw5_h2database.repositories.StockPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository repository;

    public Iterable<StockPrice> getAllStockPrices() {
        return repository.findAll();
    }

    public StockPrice getStockPriceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public StockPrice getStockPriceByDate(String date) {
        return repository.findByDate(date);
    }

    public StockPrice addStockPrice(StockPrice stockPrice) {
        return repository.save(stockPrice);
    }

    public StockPrice updateStockPrice(Long id, StockPrice stockPrice) {
        if (repository.existsById(id)) {
            stockPrice.setId(id);
            return repository.save(stockPrice);
        }
        return null;
    }

    public void deleteStockPrice(Long id) {
        repository.deleteById(id);
    }
}