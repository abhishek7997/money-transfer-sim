package org.java.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // create 5 bank accounts
        List<BankAccount> bankAccounts = new ArrayList<>();
        for(int i=1;i<=5;i++) {
            int initialAmount = i*6417 + i*i;
            bankAccounts.add(new BankAccount(initialAmount));
        }

        // create 100 thread (users)
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(100);

        for(int i=0;i<5;i++) {

        }
    }

    private boolean transfer(BankAccount b1, BankAccount b2, int amount) {
        boolean status = b1.withdraw(amount);

        if (!status) {
            log.error("Could not withdraw amount. Not enough balance.");
            return false;
        }

        b2.deposit(amount);
        return true;
    }
}