package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class PlusFlameItem extends PowerUp {
    public PlusFlameItem(double x, double y){
        super(x, y, powerupFlames);
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
            Bomb.setFlameLength(2);
            if(time >= timePowerUp){
                eaten = false;
                Bomb.setFlameLength(1);
            }
        }

    }
}
