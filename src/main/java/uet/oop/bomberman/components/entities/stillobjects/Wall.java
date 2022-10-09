package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Wall extends Entity {
    private static List<Image> walls;
    private static boolean initialized = false;
    private final int level;

    public static void init() {
        if (!initialized) {
            walls = new ArrayList<>();
            SpriteSheet newTiles = new SpriteSheet("/spriteSheet/TilesMap.png", 96, 96);
            try {
                for (int i = 1; i <= 3; i++) {
                    walls.add(new Image(LevelMap.class.getResource("/sprites/map/wall/wall" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException | NullPointerException e) {
                System.out.println("Wall init");
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public Wall(int x, int y, int width, int height, int level){
        super(x, y, width, height);
        this.level = level;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(walls.get(level - 1), x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {

    }
}
