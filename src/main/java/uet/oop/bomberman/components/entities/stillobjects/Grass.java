package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.util.LinkedList;

public class Grass extends Entity {
    public static LinkedList<Image> grasses;
    public static boolean INIT = false;
    private int lever;

    public static void init() {
        if (!INIT) {
            grasses = new LinkedList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 0, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 1, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 2, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 3, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, SpriteSheet.newTiles, 32, 32).getFxImage());
            grasses.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, SpriteSheet.newTiles, 32, 32).getFxImage());
            INIT =true;
        }
    }
    public Grass(int x, int y, int lever){
        super(x, y);
        this.lever = lever;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(grasses.get(lever - 1), x, y);
    }

    @Override
    public void update() {

    }
}
