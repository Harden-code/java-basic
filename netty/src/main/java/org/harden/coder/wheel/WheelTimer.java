package org.harden.coder.wheel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import static java.time.ZoneOffset.UTC;
import static org.harden.coder.wheel.TimerStatus.*;

public class WheelTimer {
    private final Bucket[] buckets;

    private final Worker worker;

    private long interval;

    private long loop;

    private static volatile TimerStatus STATUS =START;

    private static final ExecutorService EXECUTOR_SERVICE=Executors.newFixedThreadPool(1);

    private long firstTime;



    public WheelTimer(int loop,long interval){
        this.buckets =new Bucket[loop];
        this.loop=loop;
        for(int i=0;i<loop;i++){
            buckets[i]=new Bucket();
        }
        this.firstTime=LocalDateTime.now().toInstant(UTC).toEpochMilli();
        this.worker  =new Worker(this,firstTime,interval);
        this.interval=interval;
    }

    public void newWorker(TimeWorker timeWorker, LocalDateTime date){
        BiConsumer<TimeWorker,LocalDateTime> consumer=null;

        if(checkDate(date)){
            throw new IllegalArgumentException("date is error");
        }
        switch (STATUS){
            case START: consumer=this::start;
            break;
            case STARTED: consumer=this::started;
            break;
            case STOP: consumer=this::stop;
            break;
        }
        consumer.accept(timeWorker,date);
    }


    //第一次进入 需要启动线程
    private void start(TimeWorker timeWorker,LocalDateTime date){
        synchronized (this){
            //提取时间
            long firstTime=date.toInstant(UTC).toEpochMilli();
            buckets[index(firstTime)].push(new BucketEle(timeWorker,calculate(firstTime)));
            EXECUTOR_SERVICE.submit(worker);
            STATUS = STARTED;
        }
    }

    private void started(TimeWorker timeWorker,LocalDateTime date){
        synchronized (this){
            firstTime=date.toInstant(UTC).toEpochMilli();
            buckets[index(firstTime)].push(new BucketEle(timeWorker,calculate(firstTime)));
        }
    }

    private void stop(TimeWorker timeWorker,LocalDateTime date){
        synchronized (this){
            EXECUTOR_SERVICE.shutdownNow();
        }
    }
    //计算需要几圈
    private long calculate(long date){
        return  (date - firstTime) / interval /loop ;
    }

    //计算所在那个段
    private int index(long date){
        //取余数
        long index = (date - firstTime) / interval;
        return (int)index>loop?(int)(index%loop):(int)(index);
    }

    private boolean checkDate(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }


    private static class Bucket{
        private final List<BucketEle> queue =new CopyOnWriteArrayList<>();

        public BucketEle pull(int i){
            return  queue.get(i);
        }

        public void push(BucketEle bucketEle) {
            queue.add(bucketEle);
        }

        public void remove(BucketEle bucketEle){
            queue.remove(bucketEle);
        }

        public int size() {
            return queue.size();
        }
    }

    private static class BucketEle{

        private TimeWorker timeWorker;

        private long time;//圈数

        BucketEle(TimeWorker timeWorker, long time) {
            this.timeWorker = timeWorker;
            this.time = time;
        }

        /**
         * 小于等于 就需要马上执行
         * * @param i
         */
        public boolean expire(long i){
            if(time<=i){
                timeWorker.run();
                return true;
            }
            return false;
        }
    }

    private static class Worker implements Runnable{
        private WheelTimer wheelTimer;

        private long firstTime;

        private long wheelInterval;

        private long time;


        Worker(WheelTimer timer,long firstTime,long wheelInterval){
            this.wheelTimer=timer;
            this.firstTime=firstTime;
            this.wheelInterval=wheelInterval;
            this.time=0;
        }


        @Override
        public void run() {
            int i=0;
            for(;;){
                try {
                    waitForNextTick(i);
                    //少20秒
                    Bucket bucket = wheelTimer.buckets[i++];
                    if(i % wheelTimer.loop==0){//一圈跑完
                        time++;
                        i=0;
                    }
                    BucketEle ele = null;
                    boolean flag=false;
                    for(int j=0;j<bucket.size();j++){
                       ele = bucket.pull(j);
                        if (ele.expire(time)) {
                            flag=true;
                        }
                    }
                    if (flag) {
                        bucket.remove(ele);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void waitForNextTick(int i) throws InterruptedException {
            //error
            long l = (firstTime+(((time)*wheelTimer.loop*wheelInterval))+i*wheelInterval)
                    -LocalDateTime.now().toInstant(UTC).toEpochMilli();
            if(l>0){
                Thread.sleep(l);
            }

        }


    }

}
