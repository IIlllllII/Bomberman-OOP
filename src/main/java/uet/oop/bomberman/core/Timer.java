package uet.oop.bomberman.core;

/**
 * Singleton class
 * Used to keep track of the time
 */

public class Timer {
    // Needed to make Timer a singleton class
    private static Timer instance = null;
    // Now
    private long ticks;
    private double deltaTime;

    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    private Timer() {
        deltaTime = 0.0f;
        ticks = 0;
    }

    public void update(long now) {
        if (deltaTime == 0.0f) {
            deltaTime = 14.0f;
        } else {
            deltaTime = now - ticks;
        }
        ticks = now;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public long getTicks() {
        return ticks;
    }
}
