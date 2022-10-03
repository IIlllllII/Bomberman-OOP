package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Brick extends Entity {
    public static LinkedList<Image> bricks;
    public static boolean INIT = false;
    private int lever;

    public static void init() {
        if (!INIT) {
            bricks = new LinkedList<>();
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 0, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 1, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 2, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 3, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 4, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 5, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 6, SpriteSheet.newTiles, 32, 32).getFxImage());
            INIT = true;
        }
    }

    public Brick(int x, int y, int lever){
        super(x, y);
        this.lever = lever;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(bricks.get(lever -1), x, y);
    }

    @Override
    public void update() {

    }
}
