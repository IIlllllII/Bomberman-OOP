package uet.oop.bomberman.components.entities.items.item_types;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.items.Item;

public class FlameUp extends Item {

    /**
     * Increase the blast radius each bombs can make (Max. 6).
     */

    public FlameUp(double x, double y){
        super(x, y, fireUp);
    }

    @Override
    public void changePower() {
        if (Bomb.getFlameLength() < Flame.MAX_LENGTH) {
            Bomb.setFlameLength(Bomb.getFlameLength() + 1);
        }
        done = true;
    }

    @Override
    public Image getImage() {
        return fireUp;
    }
}
