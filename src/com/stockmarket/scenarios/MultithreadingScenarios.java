package com.stockmarket.scenarios;

public class MultithreadingScenarios {
    public static void main(String[] args) {
        // Scenario demonstrations
        
        // Scenario 1: Calculating Simple Moving Average for one stock
        singleStockSMA("AAPL");
        
        // Scenario 2: Calculating Simple Moving Average for multiple stocks
        multipleStocksSMA(new String[]{"AAPL", "GOOG", "MSFT", "AMZN", "META"});
        
        // Scenario 3: Real-time price update dashboard
        realTimePriceDashboard();
        
        // Scenario 4: Sequential order processing
        processOrders();
    }
    
    // Scenario 1: CPU-bound calculation for one stock - NOT ideal for multithreading
    private static void singleStockSMA(String stock) {
        System.out.println("Calculating SMA for " + stock);
        // CPU-bound calculation on a single dataset
        // Threading doesn't help here - overhead would outweigh benefits
    }
    
    // Scenario 2: CPU-bound calculations for multiple stocks - Good for multithreading
    private static void multipleStocksSMA(String[] stocks) {
        System.out.println("Calculating SMAs for multiple stocks");
        // Independent calculations on different stocks
        // GOOD candidate for multithreading
    }
    
    // Scenario 3: I/O-bound operations (API calls) - Good for multithreading
    private static void realTimePriceDashboard() {
        System.out.println("Updating real-time dashboard");
        // Multiple independent API calls
        // GOOD candidate for multithreading
    }
    
    // Scenario 4: Sequential order processing - NOT ideal for multithreading
    private static void processOrders() {
        System.out.println("Processing orders sequentially");
        // Orders must be processed in specific sequence
        // NOT a good candidate for multithreading
    }
}