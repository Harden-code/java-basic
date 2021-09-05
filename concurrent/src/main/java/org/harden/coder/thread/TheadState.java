package org.harden.coder.thread;

public class TheadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"TimeWaiting").start();

        new Thread(new Waiting(),"Waiting").start();

        new Thread(new Blocked1(),"Blocked1").start();

        new Thread(new Blocked2(),"Blocked2").start();
    }

    static class TimeWaiting implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Waiting implements Runnable{
        @Override
        public void run() {
            synchronized (this){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class Blocked1 implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked1.class){

                while (true){

                }

            }
        }
    }
    static class Blocked2 implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked1.class){

                while (true){

                }

            }
        }
    }

}
