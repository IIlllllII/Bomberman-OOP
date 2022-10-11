package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class BrickPassItem extends Item {

    public BrickPassItem(double x, double y){
        super(x, y, powerupBrickpass);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassBrick(true);
        if(time >= timePowerUp){
            EntitiesManager.getInstance().players.get(0).setCanPassBrick(false);
            eaten = false;
            done = true;
        }
    }
}
