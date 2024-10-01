package com.example.stockapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends ViewModel {
    private MutableLiveData<StockModel> stockData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<StockModel> getStockData() {
        return stockData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public void fetchStockData(String symbol, String apiKey) {
        isLoading.setValue(true);
        errorMessage.setValue(null); // Clear previous error messages

        StockAPI stockApi = RetrofitClientInstance.getRetrofitInstance().create(StockAPI.class);

        // Adjust the parameters for the API call
        Call<StockResponse> call = stockApi.getStockData("TIME_SERIES_INTRADAY", symbol, "5min", apiKey);

        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {

                    StockResponse stockResponse = response.body();
                    // Check if MetaData is null
                    if (stockResponse.getMetaData() == null) {
                        errorMessage.setValue("Invalid stock symbol or no data available");
                        return; // Exit the method early if there is no metadata
                    }
                    String lastRefreshed = stockResponse.getMetaData().getLastRefreshed();

                    // Get the time series data
                    Map<String, StockResponse.IntradayStockData> timeSeries = stockResponse.getTimeSeriesIntraday();

                    // Check if time series has enough data
                    if (timeSeries.size() >= 2) {
                        // Get the latest data (current price)
                        String latestTimestamp = Collections.max(timeSeries.keySet()); // Get the most recent timestamp
                        StockResponse.IntradayStockData latestData = timeSeries.get(latestTimestamp);
                        double currentPrice = Double.parseDouble(latestData.getClose());

                        // Get the previous data (5 minutes ago)
                        timeSeries.remove(latestTimestamp); // Remove the latest data to get the second most recent
                        String previousTimestamp = Collections.max(timeSeries.keySet()); // Get the second latest timestamp
                        StockResponse.IntradayStockData previousData = timeSeries.get(previousTimestamp);
                        double previousPrice = Double.parseDouble(previousData.getClose());

                        // Calculate the price change percentage
                        double priceChangePercentage = ((currentPrice - previousPrice) / previousPrice) * 100;

                        // Create StockModel and set the value
                        stockData.setValue(new StockModel(symbol, lastRefreshed, currentPrice, priceChangePercentage));
                    } else {
                        errorMessage.setValue("Not enough data available for price change calculation");
                    }
                } else {
                    errorMessage.setValue("Invalid stock symbol or no data available");
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue(t.getMessage());
            }
        });
    }
}
