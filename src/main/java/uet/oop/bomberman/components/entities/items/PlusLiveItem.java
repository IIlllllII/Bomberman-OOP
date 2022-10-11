package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;

public class PlusLiveItem extends Item {

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
