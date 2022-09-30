package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;

public class Brick extends Entity {
    private Sprite[] sprites = {Sprite.brick1, Sprite.brick2, Sprite.brick3, Sprite.brick4, Sprite.brick5, Sprite.brick6, Sprite.brick7};

    public Brick(int i, int j, Image fxImage) {
        super(i, j, fxImage);
    }

    public Brick(int i, int j, int lever){
        super(i, j, null);
        this.setImg(sprites[lever-1].getFxImage());
    }
    @Override
    public void update() {

    }
}
