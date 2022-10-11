package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusSpeedItem extends PowerUp{

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
