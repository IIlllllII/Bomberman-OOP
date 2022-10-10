package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class FlamePassItem extends PowerUp{

    public FlamePassItem(double x, double y){
        super(x, y, powerupFlamepass);
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
            Bomber.setCanPassFlame(true);
            if(time >= timePowerUp){
                Bomber.setCanPassFlame(false);
                eaten = false;
            }
        }
    }
}
