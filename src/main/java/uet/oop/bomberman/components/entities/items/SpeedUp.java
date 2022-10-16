package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;

public class SpeedUp extends Item {
    /**
     * Slightly increases Bomberman speed.
     */
    public SpeedUp(double x, double y){
        super(x, y, speedUp);
        setTimePowerUp(60000);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setSpeed(Bomber.DEFAULT_SPEED);
        if (time >= timePowerUp) {
            eaten = false;
            EntitiesManager.getInstance().players.get(0).setSpeed(Bomber.DEFAULT_SPEED );
            done = true;
        }
    }
}
