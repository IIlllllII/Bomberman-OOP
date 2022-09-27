package uet.oop.bomberman.components.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.graphics.Sprite;

public abstract class Entity {
    private int x;
    private int y;
    private double rotation;
    private boolean active;
    protected Image img;

    public Entity(){
        active = true;
    }
    public Entity(int x, int y, Image img) {
        this.x = x * Sprite.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        this.y = y * Sprite.SCALED_FACTOR * Sprite.DEFAULT_SIZE;
        this.img = img;
        active = true;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public double getRotation() {
        return rotation;
    }

    public boolean getActive() {
        return active;
    }

    public Image getImg() {
        return img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

}
