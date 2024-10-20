package com.example.hw7_vaadin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.hw7_vaadin.entities.StockPrice;
import com.example.hw7_vaadin.services.StockPriceService;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockPriceController {

    @Autowired
    private StockPriceService service;

    @GetMapping("/all") // get all data
    public List<StockPrice> getAllStockPrices() {
        return service.getAllStockPrices();
    }

    @GetMapping("/{id}") // get element by id
    public StockPrice getStockPriceById(@PathVariable Long id) {
        return service.getStockPriceById(id);
    }

    @GetMapping("/date/{date}") // get element by non pk field
    public List<StockPrice> getStockPriceByDate(@PathVariable String date) {
        return service.getStockPricesByDate(date);
    }

    // add
    @PostMapping("/add")
    public StockPrice addStockPrice(@RequestBody StockPrice stockPrice) {
        return service.addStockPrice(stockPrice);
    }

    // update
    @PutMapping("/update/{id}")
    public StockPrice updateStockPrice(@PathVariable Long id, @RequestBody StockPrice stockPrice) {
        return service.updateStockPrice(id, stockPrice);
    }

    // delete
    @DeleteMapping("/delete/{id}")
    public boolean deleteStockPrice(@PathVariable Long id) {
        System.out.println("Inside controller deleteStockPrice: " + id);
        return service.deleteStockPrice(id);
    }

    
}