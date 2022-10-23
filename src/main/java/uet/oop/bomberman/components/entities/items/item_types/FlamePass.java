package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

public class FlamePass extends Item {
    /**
     * Allows Bomberman to survive his own bomb blast (but NOT the enemies).
     */

    public FlamePass(double x, double y){
        super(x, y, flamePass);
        setTimePowerUp(45000);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassFlame(true);
        if(time >= timePowerUp){
            EntitiesManager.getInstance().players.get(0).setCanPassFlame(false);
            eaten = false;
            done = true;
        }
    }
}
