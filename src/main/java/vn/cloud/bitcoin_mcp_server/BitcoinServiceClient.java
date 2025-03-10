package vn.cloud.bitcoin_mcp_server;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Client service for fetching Bitcoin data from CoinGecko API.
 */
@Service
public class BitcoinServiceClient {

    private static final String BASE_URL = "https://api.coingecko.com/api/v3";
    private final RestClient restClient;

    /**
     * Constructor that initializes RestClient with CoinGecko base URL.
     * 
     * @param builder RestClient.Builder to create RestClient
     */
    public BitcoinServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(BASE_URL).build();
    }

    /**
     * Get the current Bitcoin price in the specified currency.
     * 
     * @param currency the currency code (e.g., "usd", "eur", "jpy")
     * @return the current Bitcoin price in the specified currency
     */
    @Tool(description = "Get bitcoin price by currency, Ex: USD, EUR")
    public double getBitcoinPriceByCurrency(String currency) {
        try {
            Map<String, Map<String, Integer>> response = restClient.get()
                .uri("/simple/price?ids=bitcoin&vs_currencies={currency}", currency.toLowerCase())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Map.class);

            if (response != null && response.containsKey("bitcoin")) {
                Map<String, Integer> bitcoinData = response.get("bitcoin");
                if (bitcoinData.containsKey(currency.toLowerCase())) {
                    return bitcoinData.get(currency.toLowerCase());
                }
            }
            return -1.0; // Indicates error
        } catch (Exception e) {
            System.err.println("Error fetching Bitcoin price: " + e.getMessage());
            return -1.0;
        }
    }

    /**
     * Get Bitcoin price in USD (convenience method).
     * 
     * @return the current Bitcoin price in USD
     */
    @Tool
    public double getBitcoinPrice() {
        return getBitcoinPriceByCurrency("usd");
    }

    /**
     * Get historical Bitcoin price data for the specified number of days.
     * 
     * @param days number of days of historical data
     * @return a map containing historical price data
     */
    @SuppressWarnings("unchecked")
    @Tool
    public Map<String, Object> getHistoricalBitcoinPrice(int days) {
        try {
            // Ensure days is valid
            if (days <= 0) {
                days = 7; // Default to 7 days
            }

            Map<String, Object> response = restClient.get()
                .uri("/coins/bitcoin/market_chart?vs_currency=usd&days={days}&interval=daily", days)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Map.class);

            if (response != null && response.containsKey("prices")) {
                Map<String, Object> result = new HashMap<>();
                Map<String, Double> priceByDate = new LinkedHashMap<>();
                
                // CoinGecko returns prices as [[timestamp, price], ...]
                List<List<Object>> prices = (List<List<Object>>) response.get("prices");
                
                // Format and populate the price data
                for (List<Object> priceData : prices) {
                    // The timestamp is in milliseconds
                    long timestamp = ((Number) priceData.get(0)).longValue();
                    double price = ((Number) priceData.get(1)).doubleValue();
                    
                    // Convert timestamp to date string (YYYY-MM-DD)
                    LocalDate date = java.time.Instant.ofEpochMilli(timestamp)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
                    String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                    
                    priceByDate.put(dateStr, price);
                }
                
                result.put("currency", "usd");
                result.put("days", days);
                result.put("prices", priceByDate);
                return result;
            }
            
            return Map.of("error", "Unable to retrieve historical data");
        } catch (Exception e) {
            System.err.println("Error fetching Bitcoin historical data: " + e.getMessage());
            return Map.of("error", "Failed to fetch historical data: " + e.getMessage());
        }
    }
}
