package com.example.hw7_vaadin.repositories;

import com.example.hw7_vaadin.entities.StockPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockPriceRepository extends CrudRepository<StockPrice, Long> {
    List<StockPrice> findByDate(String date);
}