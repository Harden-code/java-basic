package org.harden.coder.lock;

import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CustomLock implements Lock {

    private final CountLock countLock;

    public CustomLock(){
        countLock=new CountLock(2);
    }

    public static void main(String[] args) {
        CustomLock lock = new CustomLock();
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(6);


        for(int i=0;i<6;i++){
            final int j=i;//for lam
            executorService.submit(()->{
                lock.lock();
                System.out.println("--begin-- "+j);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--stop-- "+j);
                lock.unlock();
            });
        }


    }

    private static class CountLock extends AbstractQueuedSynchronizer{

        public CountLock(int count){
            setState(count);
        }

        @Override
        public int tryAcquireShared(int arg) {
            int oldValue=getState();
            int newValue=oldValue-arg;
            if(newValue>=0 && compareAndSetState(oldValue,newValue)){
                return newValue;
            }
            return -1;
        }

        @Override
        public boolean tryReleaseShared(int arg) {
            int oldValue=getState();
            int newValue=oldValue+arg;
            return compareAndSetState(oldValue,newValue);
        }
    }

    @Override
    public void lock() {
        countLock.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        countLock.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return countLock.tryAcquireShared(1)<0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return countLock.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        countLock.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
