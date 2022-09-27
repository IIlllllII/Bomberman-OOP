package uet.oop.bomberman.components.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.config.GameConfig;

public abstract class Entity {
    protected Image img;
    protected int x;
    protected int y;
    protected boolean active;

    public Entity() {}

    public Entity(int x, int y, Image img) {
        this.x = x * GameConfig.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        this.y = y * GameConfig.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        this.img = img;
        active = true;
    }

    public boolean getActive() {
        return active;
    }

    public Image getImg() {
        return img;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

}
