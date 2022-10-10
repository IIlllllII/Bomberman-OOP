package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusSpeedItem extends PowerUp{

    public PlusSpeedItem(double x, double y){
        super(x, y, powerupSpeed);
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
            Bomber.setSteps(8);
            if(time >= timePowerUp){
                eaten = false;
                Bomber.setSteps(4);
            }
        }
    }
}
