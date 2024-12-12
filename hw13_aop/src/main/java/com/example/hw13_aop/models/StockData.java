package com.example.hw13_aop.models;

import lombok.Data;

@Data
public class StockData {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double adjClose;
    private long volume;

    public StockData(String date, double open, double high, double low, double close, double adjClose, long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }

    public String[] toCSV() {
        return new String[]{date, String.valueOf(open), String.valueOf(high), String.valueOf(low), String.valueOf(close), String.valueOf(adjClose), String.valueOf(volume)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockData stockData = (StockData) o;

        return date.equals(stockData.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
