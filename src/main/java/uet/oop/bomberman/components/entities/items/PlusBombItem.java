package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class PlusBombItem extends Item {
    public PlusBombItem(double x, double y){
        super(x, y, powerupBombs);
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
