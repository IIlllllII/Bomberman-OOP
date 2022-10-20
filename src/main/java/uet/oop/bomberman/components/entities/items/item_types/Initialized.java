package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

public class Initialized extends Item {
    /**
     * Allows Bomberman to be immune to bomb blast and enemies.
     */

    public Initialized(double x, double y){
        super(x, y, bombPass);
        setTimePowerUp(30000);
    }
    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setInvincible(true);
        if (time >= timePowerUp) {
            EntitiesManager.getInstance().players.get(0).setInvincible(false);
            eaten = false;
            done = true;
        }
    }
}
