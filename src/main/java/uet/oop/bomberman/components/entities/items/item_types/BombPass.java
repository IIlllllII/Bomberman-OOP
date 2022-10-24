package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

public class BombPass extends Item {
    /**
     * Allows bomberman to pass through Bombs.
     */

    public BombPass(double x, double y){
        super(x, y, bombPass);
        setTimePowerUp(45000);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassBomb(true);
        if (time >= timePowerUp) {
            EntitiesManager.getInstance().players.get(0).setCanPassBomb(false);
            done = true;
        }
    }
}
