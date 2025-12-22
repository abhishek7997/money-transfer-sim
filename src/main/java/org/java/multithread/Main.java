package org.java.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("==== PROGRAM BEGIN ====");

        // create 5 bank accounts
        List<BankAccount> bankAccounts = new ArrayList<>();
        for(int i=1;i<=5;i++) {
            bankAccounts.add(new BankAccount(String.valueOf(i), 1000));
        }

        final int expectedTotal = 5 * 1000;

        // create 100 thread (users)
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(100);

        for(int i=0;i<5;i++) {
            Thread task = new Thread(new UserThread(bankAccounts));
            threadPoolExecutor.submit(task);
        }

        threadPoolExecutor.shutdown();
        try {
            while (!threadPoolExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int currTotal = 0;
        for(BankAccount b: bankAccounts) {
            currTotal += b.getBalance();
        }

        log.info("EXPECTED TOTAL: {}, ACTUAL TOTAL: {}", expectedTotal, currTotal); // without locks, actual total is usually not equal to expected total

        log.info("==== PROGRAM END ====");
    }


}