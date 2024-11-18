package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Hello world!
 */

public class App {
    public static void main(String[] args) throws CsvValidationException {
        List<Double> data = new ArrayList<>();

        //Read CSV File and take User Input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter the year (from 1980 to 2024): ");
        int year = scanner.nextInt();

        try (CSVReader reader = new CSVReader(new FileReader(Paths.get("../JPMC.csv").toFile()))) { // Read the CSV File
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String dateStr = line[0];
                String[] dateParts = dateStr.split("-");
                int fileYear = Integer.parseInt(dateParts[0]);
                int fileMonth = Integer.parseInt(dateParts[1]);
                // System.out.println(fileMonth + " " + fileYear + " " + month + " " + year);
                if (fileYear == year && fileMonth == month) {
                    try {
                        data.add(Double.parseDouble(line[4])); // Using the Closed price data
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data.isEmpty()) {
            System.out.println("No data found for the given month and year.");
            return;
        }

        // Analyze Data for given month and year
        double mean = calculateMean(data);
        double median = calculateMedian(data);
        double mode = calculateMode(data);

        try (FileWriter writer = new FileWriter("Analysis_" + month + "_" + year + ".txt")) {
            writer.write("Data Analysis of JPMC Stock Prices for " + month + "/" + year + "\n");
            writer.write("Mean: " + mean + "\n");
            writer.write("Median: " + median + "\n");
            writer.write("Mode: " + mode + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Analysis done!");
    }

    private static double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double num : data) {
            sum += num;
        }
        return data.isEmpty() ? 0 : sum / data.size();
    }

    private static double calculateMedian(List<Double> data) {
        if (data.isEmpty()) {
            return 0;
        }
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            return (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }

    private static double calculateMode(List<Double> data) {
        if (data.isEmpty()) {
            return 0;
        }
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double num : data) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        double mode = data.get(0);
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        return mode;
    }

}
