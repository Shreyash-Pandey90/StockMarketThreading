package com.stockmarket.synchronization;

import java.util.HashMap;
import java.util.Map;

public class UnsynchronizedStockTrader {
    public static void main(String[] args) {
        StockPortfolio portfolio = new StockPortfolio();
        portfolio.addShares("AAPL", 100);
        
        // Create multiple trader threads that try to sell the same shares
        for (int i = 0; i < 5; i++) {
            Thread trader = new Thread(new StockSeller(portfolio, "AAPL", 30));
            trader.start();
        }
    }
}

class StockPortfolio {
    private Map<String, Integer> shares = new HashMap<>();
    
    public void addShares(String symbol, int quantity) {
        shares.put(symbol, quantity);
        System.out.println("Portfolio now has " + quantity + " shares of " + symbol);
    }
    
    // Not synchronized - can lead to race conditions
    public boolean sellShares(String symbol, int quantity) {
        if (!shares.containsKey(symbol)) {
            return false;
        }
        
        int currentShares = shares.get(symbol);
        System.out.println("Checking " + symbol + ": " + currentShares + " shares available");
        
        if (currentShares >= quantity) {
            // This delay simulates processing time and makes race condition more likely
            try { Thread.sleep(100); } catch (InterruptedException e) { }
            
            // Without synchronization, multiple threads can reach this point
            // before any updates the shares count
            shares.put(symbol, currentShares - quantity);
            System.out.println("SOLD " + quantity + " shares of " + symbol + 
                               ", remaining: " + shares.get(symbol));
            return true;
        }
        
        System.out.println("FAILED to sell " + quantity + " shares of " + 
                          symbol + ", only " + currentShares + " available");
        return false;
    }
}

class StockSeller implements Runnable {
    private StockPortfolio portfolio;
    private String symbol;
    private int quantity;
    
    public StockSeller(StockPortfolio portfolio, String symbol, int quantity) {
        this.portfolio = portfolio;
        this.symbol = symbol;
        this.quantity = quantity;
    }
    
    @Override
    public void run() {
        portfolio.sellShares(symbol, quantity);
    }
}