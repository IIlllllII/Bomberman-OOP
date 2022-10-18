package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.players.Bomber;

public class BombUp extends Item {
    /**
     * Increase the maximum number of bombs you can lay (Max. 10).
     */
    public BombUp(double x, double y){
        super(x, y, bombUp);
    }

    @Override
    public void changePower() {
        Bomber bomber = EntitiesManager.getInstance().players.get(0);
        bomber.setBombMax(bomber.getBombMax() + 1);
        eaten = false;
        done = true;
    }
}
