package com.stockmarket.basics;

public class SingleThreadedStockUpdater {
    public static void main(String[] args) {
        String[] stocks = {"AAPL", "GOOG", "MSFT", "AMZN", "META"};
        
        long startTime = System.currentTimeMillis();
        
        // Update each stock price sequentially
        for (String stock : stocks) {
            updateStockPrice(stock);
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken: " + (endTime - startTime) + "ms");
    }
    
    private static void updateStockPrice(String stockSymbol) {
        System.out.println("Updating price for " + stockSymbol);
        // Simulate API call and data processing delay
        try {
            Thread.sleep(1000); // Each stock update takes 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Updated price for " + stockSymbol);
    }
}