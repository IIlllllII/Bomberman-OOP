package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Grass extends Entity {
    public static LinkedList<Image> grasses;
    public static boolean initialized = false;
    private final int level;

    public static void init() {
        if (!initialized) {
            grasses = new LinkedList<>();
            //SpriteSheet newTiles = new SpriteSheet("/textures/TilesMap.png", 96, 96);
            try {
                grasses.add(new Image(LevelMap.class.getResource("/map/grass" +  1 + ".png").toURI().toString()));
                grasses.add(new Image(LevelMap.class.getResource("/map/grass" +  2 + ".png").toURI().toString()));
                grasses.add(new Image(LevelMap.class.getResource("/map/grass" +  3 + ".png").toURI().toString()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            initialized =true;
        }
    }
    public Grass(int x, int y, int width, int height){
        super(x, y, width, height);
        this.level = LevelMap.getInstance().getLevel();
    }

    public Grass(int x, int y, int width, int height, int level) {
        super(x, y, width, height);
        this.level = level;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(grasses.get(level - 1), x - camera.getX(), y - camera.getY());
    }

    @Override
    public void update() {

    }
}
