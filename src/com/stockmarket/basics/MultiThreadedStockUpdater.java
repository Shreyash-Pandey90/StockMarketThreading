package com.stockmarket.basics;

public class MultiThreadedStockUpdater {
    public static void main(String[] args) {
        String[] stocks = {"AAPL", "GOOG", "MSFT", "AMZN", "META"};
        
        long startTime = System.currentTimeMillis();
        
        // Create and start a thread for each stock
        for (String stock : stocks) {
            // Create a Runnable implementation
            StockUpdater updater = new StockUpdater(stock);
            
            // Create a Thread with the Runnable
            Thread thread = new Thread(updater);
            
            // Start the thread
            thread.start();
        }
        
        // Wait for all threads to complete (simplified approach)
        try {
            Thread.sleep(1100);  // Slightly more than the individual thread time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken: " + (endTime - startTime) + "ms");
    }
}

// Implementing the Runnable interface
class StockUpdater implements Runnable {
    private String stockSymbol;
    
    public StockUpdater(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
    // Implementing the run() method
    @Override
    public void run() {
        System.out.println("Updating price for " + stockSymbol);
        // Simulate API call and data processing delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Updated price for " + stockSymbol);
    }
}