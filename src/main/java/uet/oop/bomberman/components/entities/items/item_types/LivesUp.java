package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.bomber.Bomber;

public class LivesUp extends Item {
    /**
     * Increase Bomberman lives.
     */
    public LivesUp(double x, double y) {
        super(x, y, livesUp);
    }

    @Override
    public void changePower() {
        Bomber bomber = EntitiesManager.getInstance().bombers.get(0);
        bomber.setLives(bomber.getLives() + 1);
        done = true;
    }
}
