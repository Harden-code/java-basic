package org.harden.coder.thread;

public class WaitNotify {
    private static boolean flag =false;

    public static void main(String[] args)  {
        new Thread(new WaitClass(),"wait").start();

        new Thread(new NotifyClass(),"notify").start();
    }

    static class WaitClass implements Runnable{
        @Override
        public void run() {
            synchronized (WaitNotify.class){
                while (!flag){
                    System.out.println("wait...");
                    try {
                        WaitNotify.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("wait end");
        }
    }

    static class NotifyClass implements Runnable{

        @Override
        public void run() {

            synchronized (WaitNotify.class){
                System.out.println("start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag=true;
                System.out.println("notify...");
                WaitNotify.class.notifyAll();
            }
        }
    }
}
