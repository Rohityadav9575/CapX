package com.example.stockapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAPI {

    @GET("query") // Replace with the actual endpoint if different
    Call<StockResponse> getStockData(
            @Query("function") String function, // e.g., "TIME_SERIES_INTRADAY"
            @Query("symbol") String symbol,      // e.g., "AAPL"
            @Query("interval") String interval,  // e.g., "5min"
            @Query("apikey") String apiKey       // Your API key
    );
}
