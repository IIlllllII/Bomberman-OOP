package uet.oop.bomberman.components.entities.stillobjects;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Brick extends Entity {
    private Image image;
    public static LinkedList<Image> bricks;
    public static LinkedList<Image> brickExplodes;
    public static boolean INIT = false;
    private int lever;

    public static void init() {
        if (!INIT) {
            bricks = new LinkedList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 0, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 1, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 2, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 3, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 4, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 5, SpriteSheet.newTiles, 32, 32).getFxImage());
            bricks.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 6, SpriteSheet.newTiles, 32, 32).getFxImage());
            SpriteSheet tiles = new SpriteSheet("/resources/textures/classic.png", 256, 256);
            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 16, 16).getFxImage());
            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 16, 16).getFxImage());
            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 16, 16).getFxImage());


            INIT = true;
        }
    }

    private boolean destroyed = false;// bị phá hủy chưa
    private int timeDestroyed ;// thời gian phá hủy
    private AnimationTimer time;

    public Brick(int x, int y, int lever){
        super(x, y);
        this.lever = lever;
        image = bricks.get(lever-1);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    @Override
    public void update() {

        //image = Sprite.movingSprite(brickExplodes, timeDestroyed,time.  )
    }
}
