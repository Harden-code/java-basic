package org.harden.coder.thread;

public class InterruptThread {
    public static void main(String[] args) {
        BusThread busThread = new BusThread();
        SleepThread sleepThread = new SleepThread();
        busThread.start();
        sleepThread.start();
        busThread.interrupt();
        sleepThread.interrupt();


    }

    static class BusThread extends Thread{

        @Override
        public void run() {
            while (!this.isInterrupted()){

            }
            System.out.println("BusThread isInterrupted  "+this.isInterrupted()); // true
            System.out.println("BusThread interrupted  "+BusThread.interrupted()); //true
            System.out.println("BusThread isInterrupted again  "+this.isInterrupted()); // true
        }
    }

    static class SleepThread extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                System.out.println("SleepThread isInterrupted  "+this.isInterrupted()); //false
                System.out.println("SleepThread interrupted  "+SleepThread.interrupted());//false
                System.out.println("SleepThread isInterrupted again  "+this.isInterrupted());
                e.printStackTrace();
            }
        }
    }
}
