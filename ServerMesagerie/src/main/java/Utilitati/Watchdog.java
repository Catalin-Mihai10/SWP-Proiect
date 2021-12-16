package Utilitati;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Watchdog {
    private AtomicInteger countDownTime;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Watchdog(AtomicInteger timeout){
        countDownTime = timeout;
    }

    public void start(){

        final Runnable runnable = () -> {
            countDownTime.decrementAndGet();

            if(countDownTime.get() <= 0)
                scheduler.shutdown();
        };

        scheduler.scheduleAtFixedRate(runnable, Constants.ZERO_SECONDS, Constants.ONE_MILISECOND, TimeUnit.MILLISECONDS);
    }

    public void setCountDownTime(AtomicInteger timeout){
        countDownTime = timeout;
    }

    public AtomicInteger getCountDownTime(){
        return countDownTime;
    }
}
