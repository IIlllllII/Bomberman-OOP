package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class FlamePassItem extends Item {

    public FlamePassItem(double x, double y){
        super(x, y, powerupFlamepass);
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
