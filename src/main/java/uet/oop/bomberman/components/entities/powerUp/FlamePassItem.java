package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class FlamePassItem extends PowerUp{

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
