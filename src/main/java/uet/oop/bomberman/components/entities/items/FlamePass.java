package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class FlamePass extends Item {
    /**
     * Allows Bomberman to survive his own bomb blast (but NOT the enemies).
     */

    public FlamePass(double x, double y){
        super(x, y, flamePass);
        setTimePowerUp(60000);
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
