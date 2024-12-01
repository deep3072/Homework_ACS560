package com.example.hw12.models;

import lombok.Data;

@Data
public class StockData {
    // Model class
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;

    public StockData(String date, double open, double high, double low, double close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "date='" + date + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                '}';
    }
}
