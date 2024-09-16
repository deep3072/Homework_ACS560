package com.example.hw3.repositories;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.example.hw3.models.StockData;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;

@Repository
public class StockRepository {

    private static List<StockData> stockDataList;

    static {
        if (stockDataList == null) {
            stockDataList = initializeStockData();
        }
    }

    public static List<StockData> getStockData() {
        return stockDataList;
    }

    public static List<StockData> getStockData(Predicate<StockData> predicate) {
        return stockDataList.stream().filter(predicate).toList();
    }

    private static List<StockData> initializeStockData() {
        List<StockData> stocks = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("D:\\MS\\Fall 2024\\Software Engineering\\github\\hw3\\JPMC.csv"))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                String date = line[0];
                double open = Double.parseDouble(line[1]);
                double high = Double.parseDouble(line[2]);
                double low = Double.parseDouble(line[3]);
                double close = Double.parseDouble(line[4]);
                double adjClose = Double.parseDouble(line[5]);
                long volume = Long.parseLong(line[6]);
                StockData stock = new StockData(date, open, high, low, close, adjClose, volume);
                stocks.add(stock);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public static void addStockData(StockData stockData) {
        if (stockDataList.contains(stockData)) {
            throw new IllegalArgumentException("Error adding stock data - stock data already exists");
        }

        if (saveStockData(stockData)) {
            stockDataList.add(stockData);
        } else {
            throw new RuntimeException("Error adding stock data");
        }
    }

    public static void updateStockData(StockData stockData) {
        int index = stockDataList.indexOf(stockData);

        if (index == -1) {
            throw new IllegalArgumentException("Error updating stock data - does not exist");
        }

        StockData backup = stockDataList.get(index);
        stockDataList.set(index, stockData);

        if (!saveStockDataList()) {
            stockDataList.set(index, backup);
            throw new RuntimeException("Error updating stock data");
        }
    }

    public static void deleteStockData(StockData stockData) {
        if (!stockDataList.remove(stockData)) {
            throw new IllegalArgumentException("Error deleting stock data - does not exist");
        }

        if (!saveStockDataList()) {
            stockDataList.add(stockData);
            throw new RuntimeException("Error deleting stock data");
        }
    }

    private static StatefulBeanToCsv<StockData> createWriter(FileWriter writer) {
        ColumnPositionMappingStrategy<StockData> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(StockData.class);
        mappingStrategy.setColumnMapping(new String[]{"date", "open", "high", "low", "close", "adjClose", "volume"});

        return new StatefulBeanToCsvBuilder<StockData>(writer)
                .withMappingStrategy(mappingStrategy)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
    }

    private static boolean saveStockData(StockData stockData) {
        boolean isSaved = false;

        try (FileWriter writer = new FileWriter("D:\\MS\\Fall 2024\\Software Engineering\\github\\hw3\\JPMC.csv", true)) {
            StatefulBeanToCsv<StockData> beanWriter = createWriter(writer);
            beanWriter.write(stockData);
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println(e.getMessage());
        }

        return isSaved;
    }

    private static boolean saveStockDataList() {
        boolean isSaved = false;

        try (FileWriter writer = new FileWriter("D:\\MS\\Fall 2024\\Software Engineering\\github\\hw3\\JPMC.csv")) {
            StatefulBeanToCsv<StockData> beanWriter = createWriter(writer);
            beanWriter.write(stockDataList);
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.err.println(e.getMessage());
        }

        return isSaved;
    }
}