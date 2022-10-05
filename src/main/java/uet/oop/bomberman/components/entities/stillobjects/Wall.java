package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.util.LinkedList;

public class Wall extends Entity {
    public static LinkedList<Image> walls;
    public static boolean initialized = false;
    private final int level;

    public  static  void init() {
        if (!initialized) {
            walls = new LinkedList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 0, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 1, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 2, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 3, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 4, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 5, newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 6, newTiles, 32, 32).getFxImage());
            initialized = true;
        }
    }
    public Wall(int x, int y, int level) {
        super(x, y);
        this.level = level;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(walls.get(level -1),x, y);
    }

    @Override
    public void update() {

    }
}
