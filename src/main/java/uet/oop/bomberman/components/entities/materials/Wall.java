package uet.oop.bomberman.components.entities.materials;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

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
            try {
                SpriteSheet newTiles = new SpriteSheet("/spriteSheet/TilesMap.png", 96, 96);
                walls.add(new Image(LevelMap.class.getResource("/sprites/map/wall/wall1.png").toURI().toString()));
                walls.add(new Image(LevelMap.class.getResource("/sprites/map/wall/wall2.png").toURI().toString()));
                walls.add(new Sprite(16, 2 * 16, 1 * 16, newTiles).getFxImage());
                walls.add(new Image(LevelMap.class.getResource("/sprites/map/wall/wall3.png").toURI().toString()));
                walls.add(new Sprite(16, 2 * 16, 2 * 16, newTiles).getFxImage());
                walls.add(new Sprite(16, 2 * 16, 5 * 16, newTiles).getFxImage());
                walls.add(new Sprite(16, 4 * 16, 0 * 16, newTiles).getFxImage());
                //Map boss
                walls.add(new Sprite(16, 4 * 16, 0 * 16, newTiles).getFxImage());
                walls.add(new Sprite(16, 4 * 16, 0 * 16, newTiles).getFxImage());

//                walls.add(new Sprite(16, 2 * 16, 0 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 2 * 16, 1 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 2 * 16, 2 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 2 * 16, 3 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 2 * 16, 4 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 2 * 16, 5 * 16, newTiles, 16, 16).getFxImage());
//                walls.add(new Sprite(16, 4 * 16, 0 * 16, newTiles, 16, 16).getFxImage());
            } catch (URISyntaxException | NullPointerException e) {
                System.out.println("Wall init");
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public Wall(int x, int y, int level){
        super(x, y);
        this.level = level;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(walls.get(level - 1), x - camera.getX(), y - camera.getY()
                , GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
    }

    @Override
    public void update() {

    }
}
