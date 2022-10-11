package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

public class Portal extends Entity {
    public static boolean initialized = false;
    public static Image portal;
    public static void init() {
        if (! initialized) {
            SpriteSheet tiles = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
            portal = new Sprite(16, 4 * 16, 0, tiles, 14, 14).getFxImage();
            initialized = true;
        }
    }

    public Portal(int x, int y){
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(portal, x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {

    }
}
