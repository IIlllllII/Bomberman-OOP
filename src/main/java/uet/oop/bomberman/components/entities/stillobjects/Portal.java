package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

public class Portal extends Entity {
    public static boolean initialized = false;
    private static Image portal;
    private boolean appear = false;
    private boolean canPass = false;

    public static void init() {
        if (!initialized) {
            SpriteSheet tiles = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
            portal = new Sprite(16, 4 * 16, 0, tiles, 15, 15).getFxImage();
            initialized = true;
        }
    }

    public Portal(double x, double y) {
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (appear) {
            gc.drawImage(portal, x - camera.getX(), y - camera.getY());
        }
    }

    @Override
    public void update() {

    }

    public boolean isAppear() {
        return appear;
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }
}
