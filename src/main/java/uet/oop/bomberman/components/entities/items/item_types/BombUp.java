package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.bomber.Bomber;

public class BombUp extends Item {
    /**
     * Increase the maximum number of bombs you can lay (Max. 10).
     */
    public BombUp(double x, double y){
        super(x, y, bombUp);
    }

    @Override
    public void changePower() {
        Bomber bomber = EntitiesManager.getInstance().bombers.get(0);
        if(bomber.getBombMax() < 10){
            bomber.setBombMax(bomber.getBombMax() + 1);
        }
        done = true;
    }
}
