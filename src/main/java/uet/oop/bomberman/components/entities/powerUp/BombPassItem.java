package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class BombPassItem extends PowerUp {

    public BombPassItem(double x, double y){
        super(x, y, powerupBombpass);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setCanPassBomb(true);
        if(time >= timePowerUp){
            EntitiesManager.getInstance().players.get(0).setCanPassBomb(false);
            eaten = false;
            done = true;
        }
    }
}
