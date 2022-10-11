package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusBombItem extends PowerUp{
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
