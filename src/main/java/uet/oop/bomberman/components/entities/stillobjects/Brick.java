package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.Timer;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Brick extends Entity {
    private Image image;
    public static LinkedList<Image> bricks;
    public static LinkedList<Image> brickExplodes;
    public static boolean initialized = false;
    private final int level;
    private boolean destroyed = false; // bị phá hủy chưa
    private final double timeDestroyed = 1000; // thời gian phá hủy
    private double time = 0;

    public static void init() {
        if (!initialized) {
            bricks = new LinkedList<>();
            //SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            try {
                for (int i = 1; i <= 3; i++) {
                    bricks.add(new Image(LevelMap.class.getResource("/map/brick" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException | NullPointerException e) {
                e.printStackTrace();
            }

            //SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
//            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, tiles, 16, 16).getFxImage());
//            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, tiles, 16, 16).getFxImage());
//            brickExplodes.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, tiles, 16, 16).getFxImage());
            initialized = true;
        }
    }
    public Brick(int x, int y, int level) {
        super(x, y);
        this.level = level;
        image = bricks.get(level - 1);
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
        time += Timer.getInstance().getDeltaTime();
        if(destroyed){
            image = Sprite.animate(brickExplodes, time, timeDestroyed);
            if(time == timeDestroyed){
                image = null;
            }
        }
    }

    public Image getImage() {
        return image;
    }
}
