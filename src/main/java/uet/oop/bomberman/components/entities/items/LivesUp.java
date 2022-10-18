package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;

public class LivesUp extends Item {
    /**
     * Increase Bomberman lives.
     */
    public LivesUp(double x, double y) {
        super(x, y, livesUp);
    }

    @Override
    public void changePower() {
        Bomber bomber = EntitiesManager.getInstance().players.get(0);
        bomber.setLives(bomber.getLives() + 1);
        eaten = false;
        done = true;
    }
}
