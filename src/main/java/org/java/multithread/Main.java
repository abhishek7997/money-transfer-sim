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
            int initialAmount = i*1417 + i*i;
            bankAccounts.add(new BankAccount(String.valueOf(i), initialAmount));
        }

        // create 100 thread (users)
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(100);

        for(int i=0;i<5;i++) {
            Thread task = new Thread(new UserThread(bankAccounts));
            threadPoolExecutor.submit(task);
        }

        threadPoolExecutor.shutdown();
        try {
            while (!threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Not yet. Still waiting for termination");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("==== PROGRAM END ====");
    }


}