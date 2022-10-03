package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;

public class Portal extends Entity {
    public static boolean INIT = false;
    public static Image portal ;
    public static void init(){
        if(!INIT){
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            portal = new Sprite(Sprite.DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14).getFxImage();
            INIT = true;
        }
    }

    public Portal(int x, int y){
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(portal, x, y);
    }

    @Override
    public void update() {

    }
}
