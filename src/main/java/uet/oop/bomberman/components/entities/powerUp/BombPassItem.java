package uet.oop.bomberman.components.entities.powerUp;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.core.Timer;

public class BombPassItem extends PowerUp {

    public BombPassItem(double x, double y){
        super(x, y, powerupBombpass);
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
            Bomber.setCanPassBom(true);
            if(time >= timePowerUp){
                Bomber.setCanPassBom(false);
                eaten = false;
            }
        }
    }

}
