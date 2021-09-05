package org.harden.coder.thread;

public class JoinThread {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Join(), "join");

        thread.start();
        thread.join();
        System.out.println("..");

    }
    static class Join implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("run..");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
