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

public class App {

    public static void main(String[] args) throws CsvValidationException {
        Scanner scanner = new Scanner(System.in);

        // Read user input
        int month = getUserInput("Enter the month (1-12): ", scanner);
        int year = getUserInput("Enter the year (from 1980 to 2024): ", scanner);

        // Process CSV and get data for the given month and year
        List<Double> data = readDataFromCSV(month, year);

        if (data.isEmpty()) {
            System.out.println("No data found for the given month and year.");
            return;
        }

        // Analyze Data
        double mean = calculateMean(data);
        double median = calculateMedian(data);
        double mode = calculateMode(data);

        // Write results to a file
        writeAnalysisToFile(month, year, mean, median, mode);

        System.out.println("Analysis done!");
    }

    // Get user input for month and year
    private static int getUserInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    // Read data from CSV file for a specific month and year
    private static List<Double> readDataFromCSV(int month, int year) throws CsvValidationException {
        List<Double> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(Paths.get("../JPMC.csv").toFile()))) {
            String[] line;
            reader.readNext(); // Skip header line
            while ((line = reader.readNext()) != null) {
                String dateStr = line[0];
                String[] dateParts = dateStr.split("-");
                int fileYear = Integer.parseInt(dateParts[0]);
                int fileMonth = Integer.parseInt(dateParts[1]);

                if (fileYear == year && fileMonth == month) {
                    try {
                        data.add(Double.parseDouble(line[4])); // Using the Close price data
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Calculate mean of the data
    private static double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double num : data) {
            sum += num;
        }
        return data.isEmpty() ? 0 : sum / data.size();
    }

    // Calculate median of the data
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

    // Calculate mode of the data
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

    // Write the analysis results to a file
    private static void writeAnalysisToFile(int month, int year, double mean, double median, double mode) {
        try (FileWriter writer = new FileWriter("Analysis_" + month + "_" + year + ".txt")) {
            writer.write("Data Analysis of JPMC Stock Prices for " + month + "/" + year + "\n");
            writer.write("Mean: " + mean + "\n");
            writer.write("Median: " + median + "\n");
            writer.write("Mode: " + mode + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
