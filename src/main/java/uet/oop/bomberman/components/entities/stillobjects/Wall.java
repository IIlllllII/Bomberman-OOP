package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.util.LinkedList;
import java.util.List;

public class Wall extends Entity {
    public static LinkedList<Image> walls;
    public static boolean INIT = false;
    private int lever;

    public  static  void init(){
        if(!INIT){
            walls = new LinkedList<>();
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 0, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 1, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 2, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 3, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 4, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 5, SpriteSheet.newTiles, 32, 32).getFxImage());
            walls.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 6, SpriteSheet.newTiles, 32, 32).getFxImage());

        }
    }
    public Wall(int x, int y, int lever){
        super(x, y);
        this.lever = lever;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(walls.get(lever -1),x, y);
    }

    @Override
    public void update() {

    }
}
