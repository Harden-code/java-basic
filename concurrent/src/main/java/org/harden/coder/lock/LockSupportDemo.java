package org.harden.coder.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ParkThread());
        thread.start();


        System.out.println("park");
        Thread.sleep(2000);


        LockSupport.unpark(thread);
    }

    static class ParkThread implements Runnable{

        @Override
        public void run() {
            while (true){
                System.out.println("begin...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();

            }
        }
    }
}
