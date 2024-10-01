package com.example.hw5_h2database.repositories;

import com.example.hw5_h2database.entities.StockPrice;
import org.springframework.data.repository.CrudRepository;

public interface StockPriceRepository extends CrudRepository<StockPrice, Long> {
    StockPrice findByDate(String date);
}