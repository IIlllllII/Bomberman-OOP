package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class BrickPassItem extends PowerUp {

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
