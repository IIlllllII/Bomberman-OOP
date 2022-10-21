package uet.oop.bomberman.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Clock {
    private long timeLeft; //seconds
    private static Clock instance = null;
    private final Timer timer = new Timer();
    private static final int DELAY = 1000; //milliseconds
    private static final int PERIOD = 1000; //milliseconds

    private boolean pause;

    private Clock() {
        pause = false;
    }

    public static Clock getInstance() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    timeLeft--;
                }
                if (timeLeft <= 0) {
                    timer.cancel();
                }
            }
        }, DELAY, PERIOD);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    @Override
    public String toString() {
        long min = TimeUnit.SECONDS.toMinutes(timeLeft);
        long sec = timeLeft - (min * 60);
        return format(min) + ":" + format(sec);
    }

    private String format(long value) {
        if (value < 10) {
            return "0" + value;
        }
        return value + "";
    }
}
