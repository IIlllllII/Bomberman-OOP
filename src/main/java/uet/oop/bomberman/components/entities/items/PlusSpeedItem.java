package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class PlusSpeedItem extends Item {

    public PlusSpeedItem(double x, double y){
        super(x, y, powerupSpeed);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setSteps(8);
        if(time >= timePowerUp){
            eaten = false;
            EntitiesManager.getInstance().players.get(0).setSteps(4);
            done = true;
        }
    }
}
