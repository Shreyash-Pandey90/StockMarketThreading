package com.stockmarket.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // Generate a larger list of stocks
        List<String> stocks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            stocks.add("STOCK" + i);
        }
        
        System.out.println("Running with one thread per stock (1000 threads):");
        runWithUnlimitedThreads(stocks);
        
        System.out.println("\nRunning with a thread pool (10 threads):");
        runWithThreadPool(stocks, 10);
    }
    
    private static void runWithUnlimitedThreads(List<String> stocks) {
        long startTime = System.currentTimeMillis();
        
        // Create 1000 threads - one for each stock
        List<Thread> threads = new ArrayList<>();
        for (String stock : stocks) {
            Thread thread = new Thread(new LightweightStockUpdater(stock));
            threads.add(thread);
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + "ms");
    }
    
    private static void runWithThreadPool(List<String> stocks, int poolSize) {
        long startTime = System.currentTimeMillis();
        
        // Create a thread pool with fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        
        // Submit tasks to the thread pool
        for (String stock : stocks) {
            executor.submit(new LightweightStockUpdater(stock));
        }
        
        // Initiate an orderly shutdown
        executor.shutdown();
        
        // Wait for all tasks to complete
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + "ms");
    }
}

class LightweightStockUpdater implements Runnable {
    private String stockSymbol;
    
    public LightweightStockUpdater(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    
    @Override
    public void run() {
        // Very light simulation, just to demonstrate thread overhead
        // No sleep to show pure threading overhead
        double price = Math.random() * 1000;
    }
}