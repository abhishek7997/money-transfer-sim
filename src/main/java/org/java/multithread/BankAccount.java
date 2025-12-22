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
        int curr = this.balance;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        curr += amount;
        this.balance = curr;
//        log.info("Balance after deposit: {}", this.balance);
    }

    public boolean withdraw(int amount) {
        int curr = this.balance;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (curr >= amount) {
            curr -= amount;
            this.balance = curr;
//            log.info("Balance after withdraw: {}", this.balance);
            return true;
        }

        return false;
    }
}
