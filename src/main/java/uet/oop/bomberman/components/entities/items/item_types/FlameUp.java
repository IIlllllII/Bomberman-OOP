package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.players.Bomber;

public class FlameUp extends Item {
    /**
     * Increase the blast radius each bombs can make (Max. 2).
     */

    public FlameUp(double x, double y){
        super(x, y, fireUp);
    }

    @Override
    public void changePower() {
        Bomb.setFlameLength(Bomb.getFlameLength() + 1);
        eaten = false;
        done = true;
    }
}
