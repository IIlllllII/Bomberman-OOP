package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusLiveItem extends PowerUp{

    public PlusLiveItem(double x, double y){
        super(x, y, powerupLives);
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
            Bomber.setLives(Bomber.getLives() + 1);
            eaten = false;
        }
    }
}
