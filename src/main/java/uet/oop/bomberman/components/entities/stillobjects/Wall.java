package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Wall extends Entity {
    public static LinkedList<Image> walls;
    public static boolean initialized = false;
    private final int level;

    public  static  void init() {
        if (!initialized) {
            walls = new LinkedList<>();
            SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            try {
                for (int i = 1; i <= 3; i++) {
                    walls.add(new Image(LevelMap.class.getResource("/map/wall" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException | NullPointerException e) {
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
        gc.drawImage(walls.get(level -1),x, y);
    }

    @Override
    public void update(LevelMap levelMap) {

    }
}
