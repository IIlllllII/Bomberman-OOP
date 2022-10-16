package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.bomb.Bomb;

public class FlameUp extends Item {
    /**
     * Increase the blast radius each bombs can make (Max. 2).
     */

    public FlameUp(double x, double y){
        super(x, y, fireUp);
        setTimePowerUp(60000);
    }

    @Override
    public void changePower() {
        Bomb.setFlameLength(2);
        if(time >= timePowerUp){
            eaten = false;
            Bomb.setFlameLength(1);
            done = true;
        }
    }
}
