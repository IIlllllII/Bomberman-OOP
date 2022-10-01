package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

import java.util.LinkedList;
import java.util.List;

public class Wall extends Entity {
    private LinkedList<Image> walls;

    private int lever;
    public Wall(int x, int y){
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        //gc.drawImage(walls[],x, y);
    }

    @Override
    public void update() {

    }
}
