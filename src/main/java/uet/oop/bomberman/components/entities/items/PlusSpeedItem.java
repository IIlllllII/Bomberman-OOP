package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;

public class PlusSpeedItem extends Item {

    public PlusSpeedItem(double x, double y){
        super(x, y, powerupSpeed);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setSpeed(4);
        if (time >= timePowerUp) {
            eaten = false;
            EntitiesManager.getInstance().players.get(0).setSpeed(Bomber.DEFAULT_SPEED);
            done = true;
        }
    }
}
