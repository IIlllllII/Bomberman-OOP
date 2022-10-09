package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.Timer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Brick extends Entity {
    private Image image;
    private static List<Image> bricks;
    private static List<Image> brickExplodes;
    private static boolean initialized = false;
    private boolean destroyed = false; // bị phá hủy chưa
    private final double timeDestroyed = 600; // thời gian phá hủy
    private double time = 0;
    private final int level;

    public static void init() {
        if (!initialized) {
            bricks = new ArrayList<>();
            brickExplodes = new ArrayList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            try {
                bricks.add(new Image(LevelMap.class.getResource("/map/brick1.png").toURI().toString()));
                bricks.add(new Image(LevelMap.class.getResource("/map/brick2.png").toURI().toString()));
                bricks.add(new Sprite(16, 16, 3 * 16, newTiles, 16, 16).getFxImage());

                brickExplodes.add(new Sprite(16, 7 * 16, 1 * 16, tiles, 16, 16).getFxImage());
                brickExplodes.add(new Sprite(16, 7 * 16, 2 * 16, tiles, 16, 16).getFxImage());
                brickExplodes.add(new Sprite(16, 7 * 16, 3 * 16, tiles, 16, 16).getFxImage());
            } catch (URISyntaxException | NullPointerException e) {
                System.out.println("brick init");
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public Brick(double x, double y, int width, int height) {
        super(x, y, width, height);
        this.level = LevelMap.getInstance().getLevel();
        image = bricks.get(level - 1);
    }

    public Brick(double x, double y, int width, int height, int level) {
        super(x, y, width, height);
        this.level = level;
        image = bricks.get(level - 1);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {
        if (destroyed) {
            time += Timer.getInstance().getDeltaTime();
            image = Sprite.animate(brickExplodes, time, timeDestroyed);
            if (time >= timeDestroyed) {
                image = null;
            }
        }
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
