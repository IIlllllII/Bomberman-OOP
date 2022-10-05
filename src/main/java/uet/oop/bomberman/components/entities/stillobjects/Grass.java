package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.util.LinkedList;

public class Grass extends Entity {
    public static LinkedList<Image> grasses;
    public static boolean initialized = false;
    private final int level;

    public static void init() {
        if (!initialized) {
            grasses = new LinkedList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 0, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 1, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 2, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 3, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, newTiles, 32, 32).getFxImage());
            initialized =true;
        }
    }
    public Grass(int x, int y, int level){
        super(x, y);
        this.level = level;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(grasses.get(level - 1), x, y);
    }

    @Override
    public void update() {

    }
}
