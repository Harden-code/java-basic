package org.harden.coder.thread;

public class DaemonThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new Daemon(),"daemon");

        thread.setDaemon(true);

        thread.start();

    }

    static class Daemon implements Runnable {

        @Override
        public void run() {
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally{
                System.out.println("end");
            }
        }

    }
}
