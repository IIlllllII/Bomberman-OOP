package uet.oop.bomberman.components.entities.items.item_types;

import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.items.Item;

public class Invincible extends Item {
    /**
     * Allows Bomberman to be immune to bomb blast and enemies.
     */

    public Invincible(double x, double y){
        super(x, y, bombPass);
        setTimePowerUp(30000);
    }
    @Override
    public void changePower() {
        EntitiesManager.getInstance().bombers.get(0).setInvincible(true);
        if (time >= timePowerUp) {
            EntitiesManager.getInstance().bombers.get(0).setInvincible(false);
            done = true;
        }
    }

    @Override
    public Image getImage() {
        return invincible;
    }
}
