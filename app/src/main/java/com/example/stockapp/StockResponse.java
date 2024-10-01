package com.example.stockapp;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class StockResponse {
    @SerializedName("Meta Data")
    private MetaData metaData;

    @SerializedName("Time Series (5min)")
    private Map<String, IntradayStockData> timeSeriesIntraday;

    public  MetaData getMetaData() {
        return metaData;
    }

    public Map<String, IntradayStockData> getTimeSeriesIntraday() {
        return timeSeriesIntraday;
    }

    public StockModel toStockModel() {
        // Get the latest time series entry
        String latestTime = getLatestTime();
        IntradayStockData latestData = timeSeriesIntraday.get(latestTime);

        if (latestData != null) {
            double openPrice = Double.parseDouble(latestData.getOpen());
            double closePrice = Double.parseDouble(latestData.getClose());
            double changePercentage = ((closePrice - openPrice) / openPrice) * 100;

            return new StockModel(
                    metaData.getSymbol(),
                    metaData.getLastRefreshed(),
                    closePrice,
                    changePercentage
            );
        }
        return null; // Or handle accordingly
    }

    private String getLatestTime() {
        // Get the latest timestamp from the map keys
        return timeSeriesIntraday.keySet().stream()
                .max(String::compareTo)
                .orElse(null);
    }

    // MetaData class
    public static class MetaData {
        @SerializedName("1. Information")
        private String information;

        @SerializedName("2. Symbol")
        private String symbol;

        @SerializedName("3. Last Refreshed")
        private String lastRefreshed;

        @SerializedName("4. Interval")
        private String interval;

        @SerializedName("5. Output Size")
        private String outputSize;

        @SerializedName("6. Time Zone")
        private String timeZone;

        public String getInformation() {
            return information;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getLastRefreshed() {
            return lastRefreshed;
        }

        public String getInterval() {
            return interval;
        }

        public String getOutputSize() {
            return outputSize;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    // IntradayStockData class
    public static class IntradayStockData {
        @SerializedName("1. open")
        private String open;

        @SerializedName("2. high")
        private String high;

        @SerializedName("3. low")
        private String low;

        @SerializedName("4. close")
        private String close;

        @SerializedName("5. volume")
        private String volume;

        public String getOpen() {
            return open;
        }

        public String getHigh() {
            return high;
        }

        public String getLow() {
            return low;
        }

        public String getClose() {
            return close;
        }

        public String getVolume() {
            return volume;
        }
    }
}
