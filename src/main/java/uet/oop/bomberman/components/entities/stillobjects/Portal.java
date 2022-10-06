package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;

public class Portal extends Entity {
    public static boolean initialized = false;
    public static Image portal;
    public static void init() {
        if (! initialized) {
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            portal = new Sprite(Sprite.DEFAULT_SIZE, 4, 0, tiles, 14, 14).getFxImage();
            initialized = true;
        }
    }

    public Portal(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(portal, x, y);
    }

    @Override
    public void update(LevelMap levelMap) {

    }
}
