package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusLiveItem extends PowerUp {

    public PlusLiveItem(double x, double y) {
        super(x, y, powerupLives);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().players.get(0).setLives(EntitiesManager.getInstance().players.get(0).getLives() + 1);
        eaten = false;
        done = true;
    }
}
