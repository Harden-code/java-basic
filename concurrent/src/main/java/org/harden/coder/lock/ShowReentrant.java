package org.harden.coder.lock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowReentrant {
    public static void main(String[] args) throws InterruptedException, IOException {
        Lock lock = new ReentrantLock();
        Thread t1=new Thread(()->{
            lock.lock();
            try {
                Thread.sleep(400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock");
            lock.unlock();

        },"t1");

        Thread t2=new Thread(()->{
            lock.lock();
            System.out.println("lock 2");
            lock.unlock();
        },"t2");


        Thread t3=new Thread(()->{
            lock.lock();
            System.out.println("lock 3");
            lock.unlock();
        },"t3");
        t1.start();
        Thread.sleep(100);
        t2.start();
        t3.start();

        System.in.read();
    }
}
