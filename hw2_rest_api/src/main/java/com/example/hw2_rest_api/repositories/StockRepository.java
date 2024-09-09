package com.example.hw2_rest_api.repositories;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.hw2_rest_api.models.StockData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


@Repository
public class StockRepository {

    public List<StockData> loadStockDataFromCSV() {
        List<StockData> stocks = new ArrayList<>();
        // String csvFile = "JPMC.csv";
        try (CSVReader reader = new CSVReader(new FileReader("D:\\MS\\Fall 2024\\Software Engineering\\github\\hw2_rest_api\\JPMC.csv"))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String date = line[0];
                double open = Double.parseDouble(line[1]);
                double high = Double.parseDouble(line[2]);
                double low = Double.parseDouble(line[3]);
                double close = Double.parseDouble(line[4]);
                StockData stock = new StockData(date, open, high, low, close);
                stocks.add(stock);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}