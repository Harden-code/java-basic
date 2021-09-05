package org.harden.coder.wheel;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CountDownLatch;

public class WheelTimerTest {

    @Test
    public void WheelTimerTest() throws InterruptedException {
        /**
         * 10圈 10秒
         */
        WheelTimer wheelTimer = new WheelTimer(10,10000);
        CountDownLatch countDownLatch=new CountDownLatch(1);
        long begin = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
//        wheelTimer.newWorker(()->{
//            System.out.println(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()-begin);
//            countDownLatch.countDown();
//        },LocalDateTime.now().plusMinutes(1));

        wheelTimer.newWorker(()->{
            System.out.println(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()-begin);
            countDownLatch.countDown();
        },LocalDateTime.now().plusMinutes(3));
        countDownLatch.await();
    }

    @Test
    public void time() throws InterruptedException {
        System.out.println(30/9);//取整3
        System.out.println(30%8);//取余6
    }
}
