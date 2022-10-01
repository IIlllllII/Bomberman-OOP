package uet.oop.bomberman.components.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.config.GameConfig;

public abstract class Entity {
    protected double x;
    protected double y;
    protected boolean active;

    public Entity() {
        x = 0;
        y = 0;
    }

    public Entity(int x, int y) {
        this.x = x * GameConfig.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        this.y = y * GameConfig.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        active = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setLocation(int x, int y) {
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
