package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;

public class SpeedUp extends Item {
    /**
     * Slightly increases Bomberman speed.
     */
    public SpeedUp(double x, double y){
        super(x, y, speedUp);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setSpeed(4);
        if (time >= timePowerUp) {
            eaten = false;
            EntitiesManager.getInstance().players.get(0).setSpeed(Bomber.DEFAULT_SPEED * 2);
            done = true;
        }
    }
}
