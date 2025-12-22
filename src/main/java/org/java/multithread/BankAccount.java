package org.java.multithread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Data
public class BankAccount {
    private final String id;
    private int balance; // this is the initial balance
    // volatile is not enough because, two threads can read same value of x, each will increment it by 1 (its own copy, that is) and set it back. In effect, it was only incremented by 1 instead of 2!
    // volatile only guarantees that all threads will see the new value of x, which is x + 1, instead of the intended x + 2.

    // two separate locks for read and write operations on balance for the simulation of deadlock scenario
//    private final Object read = new Object();
//    private final Object write = new Object();
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(String id, int balance) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.balance = balance;
    }

    public void deposit(int amount) {
        try {
            lock.lock();
            int curr = this.balance;
            Thread.sleep(1000);
            curr += amount;
            this.balance = curr;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        // simulation of deadlock scenario
        /*
        "pool-1-thread-4@1256" prio=5 tid=0x1a nid=NA waiting for monitor entry
        java.lang.Thread.State: BLOCKED
         blocks pool-1-thread-1@1248
         blocks pool-1-thread-3@1251
         waiting for pool-1-thread-1@1248 to release lock on instance of java.lang.Object(id=1269)
        at org.java.multithread.BankAccount.withdraw(BankAccount.java:61)
          - locked <0x4f6> (a java.lang.Object)
        at org.java.multithread.UserThread.transfer(UserThread.java:47)
        at org.java.multithread.UserThread.run(UserThread.java:32)
         */
//        synchronized (read) {
//            int curr = this.balance;
//            try {
//                Thread.sleep(1000);
//                curr += amount;
//                synchronized (write) {
//                    this.balance = curr;
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        try {
//            // if method level synchronized is used here, and wait time is large, every other thread will remain blocked until this completes. Even if they had to withdraw.
//            synchronized (this) {
//                int curr = this.balance;
//                Thread.sleep(1000);
//                curr += amount;
//                this.balance = curr;
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("Balance after deposit: {}", this.balance);
    }

    public boolean withdraw(int amount) {
        // simulation of deadlock scenario
        boolean status = false;

        try {
            lock.lock();
            int curr = this.balance;
            Thread.sleep(1500);
            curr -= amount;
            this.balance = curr;
            status = true;
        } catch (InterruptedException e) {
            log.error("COULD NOT WITHDRAW | {} | {}", e.getClass(), e.getMessage());
        } finally {
            lock.unlock();
        }

        return status;
        // simulation of deadlock scenario
//        synchronized (write) {
//            int curr = this.balance;
//            try {
//                Thread.sleep(1000);
//                curr -= amount;
//                synchronized (read) {
//                    this.balance = curr;
//                    status = true;
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

//        return status;
        // simple threading using synchronized (method-level and block-level)
//        try {
////            Thread.sleep(1000);
//            synchronized (this) {
//                // even in block level synchronization, if some thread takes too long to execute, all other threads will remain blocked.
//                int curr = this.balance;
//                Thread.sleep(5000);
//                if (curr >= amount) {
//                    curr -= amount;
//                    this.balance = curr;
////                  log.info("Balance after withdraw: {}", this.balance);
//                    return true;
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        return false;
    }
}
