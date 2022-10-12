package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class BombUp extends Item {
    /**
     * Increase the maximum number of bombs you can lay (Max. 10).
     */
    public BombUp(double x, double y){
        super(x, y, bombUp);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setBombMax(3);
        if(time >= timePowerUp){
            eaten = false;
            EntitiesManager.getInstance().players.get(0).setBombMax(1);
            done = true;
        }
    }
}
