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
import java.util.List;

public class Brick extends Entity {
    private static boolean initialized = false;
    private static List<Image> bricks;
    private static List<Image> brickExplodes;

    private Image image;
    private boolean destroyed = false; // bị phá hủy chưa
    private static final float timeDestroyed = 1000.0f; // thời gian phá hủy

    private float time = 0;
    private final int level;


    public static void init() {
        if (!initialized) {
            bricks = new ArrayList<>();
            
            try {
                SpriteSheet newTiles = new SpriteSheet("/spriteSheet/TilesMap.png", 96, 96);

                bricks.add(new Image(LevelMap.class.getResource("/sprites/map/brick/brick1.png").toURI().toString()));
                bricks.add(new Image(LevelMap.class.getResource("/sprites/map/brick/brick2.png").toURI().toString()));
                bricks.add(new Sprite(16, 16, 3 * 16, newTiles, 16, 16).getFxImage());

                brickExplodes = new ArrayList<>();
                SpriteSheet explodes = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
                brickExplodes.add(new Sprite(16, 7 * 16, 1 * 16, explodes, 16, 16 ).getFxImage());
                brickExplodes.add(new Sprite(16, 7 * 16, 2 * 16, explodes, 16, 16 ).getFxImage());
                brickExplodes.add(new Sprite(16, 7 * 16, 3 * 16, explodes, 16, 16 ).getFxImage());

            } catch (URISyntaxException | NullPointerException e) {
                System.out.println("brick init");
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public Brick(double x, double y, int width, int height, int level) {
        super(x, y, width, height);
        this.level = level;
        //brickExplodes.reset();
        image = bricks.get(level - 1);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            gc.drawImage(bricks.get(level - 1), x - camera.getX(), y - camera.getY());
        } else {
            gc.drawImage(image, x - camera.getX(), y - camera.getY());
        }
    }

    @Override
    public void update() {
        if (destroyed) {
            time += Timer.getInstance().getDeltaTime();
            image = Sprite.animation(brickExplodes,time, timeDestroyed);

        }
    }
}
