package org.java.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            // pick two accounts
            BankAccount b1 = bankAccounts.get(i % bankAccounts.size());
            BankAccount b2 = bankAccounts.get((i + 1) % bankAccounts.size());
            int amount = 2*i + 3;

            Thread task = new Thread() {
                @Override
                public void run() {
                    try {
                        transfer(b1, b2, amount);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                private boolean transfer(BankAccount b1, BankAccount b2, int amount) {
                    log.info("{} : Transferring from {} to {}", Thread.currentThread().getName(), b1.getId(), b2.getId());
                    log.info("{} : b1.balance={}, b2.balance={}, transferAmount={}", Thread.currentThread().getName(), b1.getBalance(), b2.getBalance(), amount);

                    boolean status = b1.withdraw(amount);

                    if (!status) {
                        log.error("Insufficient balance.");
                        return false;
                    }

                    b2.deposit(amount);
                    return true;
                }
            };

            threadPoolExecutor.submit(task);
        }

        threadPoolExecutor.shutdown();

        log.info("==== PROGRAM END ====");
    }


}