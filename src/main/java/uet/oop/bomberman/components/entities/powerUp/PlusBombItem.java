package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusBombItem extends PowerUp{
    public PlusBombItem(double x, double y){
        super(x, y, powerupBombs);
    }

    @Override
    public void update() {
        if(appear){
            time += Timer.getInstance().getDeltaTime();
            if(time >= timeAppear || eaten){
                image = null;
                appear = false;
                time = 0;
            }
        }
        if(eaten){
            time += Timer.getInstance().getDeltaTime();
            Bomber.setBombMax(3);
            if(time >= timePowerUp){
                eaten = false;
                Bomber.setBombMax(1);
            }
        }
    }
}
