package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;

public class Wall extends Entity {
    private Sprite[] sprites = {Sprite.wall1, Sprite.wall2, Sprite.wall3, Sprite.wall4, Sprite.wall5, Sprite.wall6, Sprite.wall7};

    public Wall(int i, int j, Image fxImage) {
        super(i, j, fxImage);
    }

    public Wall(int i, int j, int lever) {
        super(i, j, null);
        this.setImg(sprites[lever - 1].getFxImage());
    }

    @Override
    public void update() {

    }
}
