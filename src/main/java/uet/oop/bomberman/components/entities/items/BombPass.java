package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class BombPass extends Item {
    /**
     * Allows bomberman to pass through Bombs.
     */

    public BombPass(double x, double y){
        super(x, y, bombPass);
        setTimePowerUp(60000);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassBomb(true);
        if (time >= timePowerUp) {
            EntitiesManager.getInstance().players.get(0).setCanPassBomb(false);
            eaten = false;
            done = true;
        }
    }
}
