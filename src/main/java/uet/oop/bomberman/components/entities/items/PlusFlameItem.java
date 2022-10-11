package uet.oop.bomberman.components.entities.items;

import uet.oop.bomberman.components.entities.bomb.Bomb;

public class PlusFlameItem extends Item {
    public PlusFlameItem(double x, double y){
        super(x, y, powerupFlames);
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
