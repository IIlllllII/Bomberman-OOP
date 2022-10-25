package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.config.Direction;

public abstract class LivingEntity extends Entity {
    //Movable
    protected double speed;
    protected Direction currentDirection;

    //Killable
    protected int lives;
    protected boolean destroyed = false;

    public LivingEntity(double x, double y) {
        super(x, y);
        lives = 1;
    }
    public LivingEntity(double x, double y, int w, int h) {
        super(x, y, w, h);
        lives = 1;
    }

    protected abstract void move();

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isKilled() {
        return lives <= 0 || destroyed;
    }

    //public abstract boolean isDestroyed();

    //public abstract void setDestroyed(boolean destroyed);

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }
}
