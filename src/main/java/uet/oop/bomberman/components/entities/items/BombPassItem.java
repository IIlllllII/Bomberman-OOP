package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class BombPassItem extends Item {

    public BombPassItem(double x, double y){
        super(x, y, powerupBombpass);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassBomb(true);
        if (time >= timePowerUp) {
            EntitiesManager.getInstance().players.get(0).setCanPassBomb(true);
            eaten = false;
            done = true;
        }
    }
}
