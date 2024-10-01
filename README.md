## Application Overview

The Stock App is designed to provide real-time stock market data to users. It fetches the current stock price and calculates the percentage change based on the stock price from 5 minutes ago. This functionality allows users to gauge the stock's performance over the short term, regardless of any fluctuations that may occur throughout the day.

### Features

- **Real-time Stock Price**: The application retrieves the latest stock price for the specified stock symbol.

- **Percentage Change Calculation**: The app computes the percentage change in stock price based on the price from 5 minutes ago using the following formula:

  **Percentage Change = ((Current Price - Price 5 Minutes Ago) / Price 5 Minutes Ago) * 100**

This calculation provides users with a clear view of how the stock is performing in relation to its price from 5 minutes ago, enabling informed investment decisions.

### Example

- If the stock price was $100 five minutes ago, and the current price is $105, the percentage change would be calculated as follows:

  **Percentage Change = ((105 - 100) / 100) * 100 = 5%**

This indicates a 5% increase in the stock price since the last recorded value from 5 minutes ago.

### User Experience

Users simply enter a stock symbol (e.g., `AAPL` for Apple Inc.) to fetch the latest data. The app will display both the current stock price and the percentage change, making it easy for users to assess the stock's performance at a glance.

By providing both current price information and contextual percentage change, the Stock App empowers users to stay informed about their investments and the market's movements.
## Setup and Run

### Prerequisites
- **Android Studio**: Ensure you have the latest version of Android Studio installed on your machine.
- **Android SDK**: The Android SDK should be included with Android Studio.
- **Alpha Vantage API Key**: You will need a valid API key for accessing stock data. Replace `YOUR_API_KEY` in the code with your actual API key. Please note that Alpha Vantage provides only 20 free API calls per day.

### Minimum SDK Requirement
- The application requires a minimum SDK version of **API 21 (Android 5.0 Lollipop)**.

### Steps to Run the App

1. **Clone the Repository**:
   ```bash
   git clone  https://github.com/Rohityadav9575/CapX.git

### Running the App on a Mobile Device

To run the app directly on your mobile device:

1. **Download the APK File**:
   - Download the APK file from the link provided in PDF send to Unstop CapX submit form .

2. **Install the APK**:
   - Open the downloaded APK file on your device.
   - If your phone shows a warning about installing apps from unknown sources, follow these steps:
     - Go to `Settings`.
     - Navigate to `Security` or `Privacy`.
     - Enable the option for `Install from Unknown Sources`.

3. **Run the App**:
   - Once installed, tap the app icon to launch the Stock App on your mobile device.
   - You can now use the app to fetch and display stock data by entering a stock symbol.

