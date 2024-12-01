package com.example.hw12.repositories;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.hw12.models.StockData;
import com.example.hw12.factories.StockDataFactory;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Repository
public class StockRepository {
    // client that uses the factory to get instances of StockData objects.
    public List<StockData> loadStockDataFromCSV() {
        List<StockData> stocks = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(Paths.get("JPMC.csv").toFile()))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                StockData stock = StockDataFactory.createStockData(line); 
                stocks.add(stock);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}