package org.java.multithread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Data
public class BankAccount {
    private final String id;
    private int balance; // this is the initial balance

    public BankAccount(String id, int balance) {
        this.id = id != null ? id : UUID.randomUUID().toString();
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
