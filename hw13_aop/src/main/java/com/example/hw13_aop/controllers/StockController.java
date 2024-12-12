package com.example.hw13_aop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hw13_aop.models.StockData;
import com.example.hw13_aop.services.StockService;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{date}")
    public ResponseEntity<StockData> getStockData(@PathVariable String date) {
        StockData stockData = stockService.getStockData(date);
        if (stockData != null) {
            return ResponseEntity.ok(stockData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StockData>> getStockData() {
        return ResponseEntity.ok(stockService.getStockData());
    }

    @PostMapping
    public ResponseEntity<StockData> add(@RequestBody StockData stockData) {
        stockService.add(stockData);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockData);
    }

    @PutMapping
    public ResponseEntity<StockData> update(@RequestBody StockData stockData) {
        stockService.update(stockData);
        return ResponseEntity.ok(stockData);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody StockData stockData) {
        stockService.delete(stockData);
        return ResponseEntity.ok().build();
    }
}