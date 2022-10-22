package uet.oop.bomberman.components.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.game.Camera;

public abstract class Entity {
    protected double x;
    protected double y;

    protected int width;
    protected int height;

    protected boolean active = true;

    protected final Camera camera = Camera.getInstance();

    public Entity() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Entity(double x, double y) {
        this.x = (x / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
        this.y = (y / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
        width = GameConfig.TILE_SIZE;
        height = GameConfig.TILE_SIZE;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract void render(GraphicsContext gc);

    public abstract void update();
}