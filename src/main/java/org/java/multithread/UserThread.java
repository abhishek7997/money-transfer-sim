package org.java.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class UserThread implements Runnable {
    private final List<BankAccount> bankAccounts;
    private final Random random;

    public UserThread(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        this.random = new Random();
    }

    @Override
    public void run() {
        int i = random.nextInt(bankAccounts.size());
        int j = random.nextInt(bankAccounts.size());

        BankAccount b1 = bankAccounts.get(i);
        BankAccount b2 = bankAccounts.get(j);
        int amount = random.nextInt(1, 1000);

        try {
            Thread.sleep(2000);
            transfer(b1, b2, amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean transfer(BankAccount b1, BankAccount b2, int amount) {
        if (b1 == b2) {
            return false;
        }

//        log.info("{} : Transferring from {} to {}", Thread.currentThread().getName(), b1.getId(), b2.getId());
        log.info("BEFORE: {} : B{}.b1.balance={}, B{}.b2.balance={}, transferAmount={}", Thread.currentThread().getName(), b1.getId(), b1.getBalance(), b2.getId(), b2.getBalance(), amount);

        boolean status = b1.withdraw(amount);

        if (!status) {
            log.error("Insufficient balance.");
            return false;
        }

        b2.deposit(amount);

        log.info("AFTER: {} : B{}.b1.balance={}, B{}.b2.balance={}, transferAmount={}", Thread.currentThread().getName(), b1.getId(), b1.getBalance(), b2.getId(), b2.getBalance(), amount);
        return true;
    }
}
