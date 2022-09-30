package uet.oop.bomberman.components.entities.stillobjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;

public class Grass extends Entity {
    private Sprite[] sprites = {Sprite.grass1, Sprite.grass2, Sprite.grass3, Sprite.grass4, Sprite.grass5, Sprite.grass6, Sprite.grass7};

    public Grass(int i, int j, Image fxImage) {
        super(i, j, fxImage);
    }

    public Grass(int i, int j, int lever){
        super(i, j, null);
        this.setImg(sprites[lever-1].getFxImage());
    }

    @Override
    public void update() {

    }
}
