package uet.oop.bomberman.core;

/**
 * Singleton class
 * Used to keep track of the time
 */

public class Timers {
    // Needed to make Timer a singleton class
    private static Timers instance = null;
    // Now
    private long ticks;
    private double deltaTime;

    public static Timers getInstance() {
        if (instance == null) {
            instance = new Timers();
        }
        return instance;
    }

    private Timers() {
        deltaTime = 0.0;
        ticks = 0;
    }

    public void update(long now) {
        if (deltaTime == 0.0) {
            deltaTime = 14.0;
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
