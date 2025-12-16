package org.java.multithread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        balance += amount;
        log.info("Balance after deposit: {}", balance);
    }

    public boolean withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            log.info("Balance after withdraw: {}", balance);
            return true;
        }

        return false;
    }
}
