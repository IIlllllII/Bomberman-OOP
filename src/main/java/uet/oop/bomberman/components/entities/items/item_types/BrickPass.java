package uet.oop.bomberman.components.entities.items.item_types;

import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

public class BrickPass extends Item {
    /**
     * Allowed Bomberman to pass through brick.
     */
    public BrickPass(double x, double y){
        super(x, y, brickPass);
        setTimePowerUp(45000);
    }

    @Override
    public void changePower() {
        EntitiesManager.getInstance().bombers.get(0).setCanPassBrick(true);
        if(time >= timePowerUp){
            EntitiesManager.getInstance().bombers.get(0).setCanPassBrick(false);
            done = true;
            EntitiesManager.getInstance().bombers.get(0).setCanResetLocation(true);
        }
    }
}
