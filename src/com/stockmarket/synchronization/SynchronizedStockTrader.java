package com.stockmarket.synchronization;

import java.util.HashMap;
import java.util.Map;

public class SynchronizedStockTrader {
    public static void main(String[] args) {
        SynchronizedPortfolio portfolio = new SynchronizedPortfolio();
        portfolio.addShares("AAPL", 100);
        
        // Create multiple trader threads that try to sell the same shares
        for (int i = 0; i < 5; i++) {
            Thread trader = new Thread(new SyncStockSeller(portfolio, "AAPL", 30));
            trader.start();
        }
    }
}

class SynchronizedPortfolio {
    private Map<String, Integer> shares = new HashMap<>();
    
    public void addShares(String symbol, int quantity) {
        shares.put(symbol, quantity);
        System.out.println("Portfolio now has " + quantity + " shares of " + symbol);
    }
    
    // Adding synchronized keyword to method
    public synchronized boolean sellShares(String symbol, int quantity) {
        if (!shares.containsKey(symbol)) {
            return false;
        }
        
        int currentShares = shares.get(symbol);
        System.out.println("Checking " + symbol + ": " + currentShares + " shares available");
        
        if (currentShares >= quantity) {
            // This delay simulates processing time
            try { Thread.sleep(100); } catch (InterruptedException e) { }
            
            // With synchronization, only one thread can execute this at a time
            shares.put(symbol, currentShares - quantity);
            System.out.println("SOLD " + quantity + " shares of " + symbol + 
                               ", remaining: " + shares.get(symbol));
            return true;
        }
        
        System.out.println("FAILED to sell " + quantity + " shares of " + 
                          symbol + ", only " + currentShares + " available");
        return false;
    }
    
    // Alternative: synchronized block example for finer control
    public boolean buySellShares(String symbol, int quantity, boolean isBuying) {
        // Only synchronize the critical section
        synchronized(this) {
            if (isBuying) {
                // Buying logic
                int currentShares = shares.getOrDefault(symbol, 0);
                shares.put(symbol, currentShares + quantity);
                System.out.println("BOUGHT " + quantity + " shares of " + symbol);
                return true;
            } else {
                // Selling logic (simplified)
                return sellShares(symbol, quantity);
            }
        }
    }
}

class SyncStockSeller implements Runnable {
    private SynchronizedPortfolio portfolio;
    private String symbol;
    private int quantity;
    
    public SyncStockSeller(SynchronizedPortfolio portfolio, String symbol, int quantity) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.quantity = quantity;
    }
    
    @Override
    public void run() {
        portfolio.sellShares(symbol, quantity);
    }
}