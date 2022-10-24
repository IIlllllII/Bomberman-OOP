package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.bomber.Bomber;

public class SpeedUp extends Item {
    /**
     * Slightly increases Bomberman speed.
     */
    private static int numberEaten = 0;
    private final static int numberEatenMax = 5;

    public SpeedUp(double x, double y){
        super(x, y, speedUp);
    }

    @Override
    public void changePower() {
        Bomber bomber = EntitiesManager.getInstance().bombers.get(0);
        numberEaten ++;
        if(numberEaten <= numberEatenMax){
            bomber.setSpeed(16.0/(8 - numberEaten));
        }
        done = true;
    }

}
