package com.example.stockapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    private StockViewModel stockViewModel;
    private EditText symbolEditText;
    private Button fetchButton;
    private TextView stockInfoTextView;
    private TextView loadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        symbolEditText = findViewById(R.id.symbolEditText);
        fetchButton = findViewById(R.id.fetchButton);
        stockInfoTextView = findViewById(R.id.stockInfoTextView);
        loadingTextView = findViewById(R.id.loadingTextView);
        loadingTextView.setVisibility(View.GONE); // Hide loading by default

        // Initialize ViewModel
        stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);

        // Observe stock data
        stockViewModel.getStockData().observe(this, new Observer<StockModel>() {
            @Override
            public void onChanged(StockModel stockModel) {
                if (stockModel != null) {
                    // Update UI with the stock data
                    String stockInfo = "Symbol: " + stockModel.getSymbol() +
                            "\nLast Refreshed: " + stockModel.getLastRefreshed() +
                            "\nCurrent Price: $" + stockModel.getCurrentPrice() +
                            "\nPrice Change (%): " + stockModel.getPriceChangePercentage() + "%";
                    stockInfoTextView.setText(stockInfo);
                }
            }
        });

        // Observe error messages
        stockViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    // Show error to user
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        // Observe loading state
        stockViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading != null) {
                    if (isLoading) {
                        loadingTextView.setVisibility(View.VISIBLE); // Show loading indicator
                        stockInfoTextView.setVisibility(View.GONE); // Hide stock info
                    } else {
                        loadingTextView.setVisibility(View.GONE); // Hide loading indicator
                        stockInfoTextView.setVisibility(View.VISIBLE); // Show stock info
                    }
                }
            }
        });

        // Fetch stock data on button click
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symbol = symbolEditText.getText().toString().trim();
                String apiKey = "9KCI0RTGOC13X19J"; // Your API key
                if (!symbol.isEmpty()) {
                    stockViewModel.fetchStockData(symbol, apiKey);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a stock symbol", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
