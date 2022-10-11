package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class Initialized extends Item{
    /**
     * Allows Bomberman to be immune to bomb blast and enemies.
     */

    public Initialized(double x, double y){
        super(x, y, bombPass);
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
