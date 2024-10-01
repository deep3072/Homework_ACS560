package com.example.hw5_h2database.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.hw5_h2database.services.StockPriceService;
import com.example.hw5_h2database.entities.StockPrice;
// import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockPriceController {

    @Autowired
    private StockPriceService service;

    // first upload data using below func
    // @PostMapping("/upload")
    // public void uploadCsv(@RequestParam("file") MultipartFile file) {
    //     service.uploadCsv(file);
    // }

    @GetMapping("/all") // get all data
    public Iterable<StockPrice> getAllStockPrices() {
        return service.getAllStockPrices();
    }

    @GetMapping("/{id}") // get element by id
    public StockPrice getStockPriceById(@PathVariable Long id) {
        return service.getStockPriceById(id);
    }

    @GetMapping("/date/{date}") // get element by non pk field
    public StockPrice getStockPriceByDate(@PathVariable String date) {
        return service.getStockPriceByDate(date);
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
    public void deleteStockPrice(@PathVariable Long id) {
        service.deleteStockPrice(id);
    }

    
}