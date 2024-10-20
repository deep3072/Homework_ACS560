package com.example.hw7_vaadin.services;

import com.example.hw7_vaadin.entities.StockPrice;
import com.example.hw7_vaadin.repositories.StockPriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockPriceService {
    @Autowired
    private StockPriceRepository repository;

    public List<StockPrice> getAllStockPrices() {
        return (List<StockPrice>)repository.findAll();
    }

    public StockPrice getStockPriceById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<StockPrice> getStockPricesByDate(String date) {
        // System.out.println("date: " + date);
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

    public boolean deleteStockPrice(Long id) {
        System.out.println("Inside service deleteStockPrice: " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            System.out.println("Deleted");
            return true;
        } else {
            System.out.println("Not found");
            return false;
        }
    }
}