package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.sound.Sound;

public class Bomb extends Entity {

    private int timeBeforeExplode = 100;
    private int timeFlame = 14;
    //private static final int timeTransfer = 40; // ???
    private boolean allowPass;  // cho phép bomber vượt qua
    private boolean explode = false;
    private Bomber bomber;
    private int flameLength = 1;

    private Sound soundPlaceBomb = new Sound(Sound.PLACE_BOMB_SOUND);
    private Sound soundExplode = new Sound(Sound.EXPLODE_SOUND);

    /**
     * Create bomb.
     */
    public Bomb(int x, int y, int flameLength, Bomber bomber){
        super(x, y);
        this.flameLength = flameLength;
        this.bomber = bomber;

    }

    @Override
    public void update() {
        if(!explode){
            if (allowPass) {
                double subX = bomber.getX() - getX();
                double subY = bomber.getY() - getY();
                // khi thay đổi đầu vào bomb và bomber sẽ phải tính lại sub
                if (subX < -20 || subX > 31 || subY > 33 || subY < -31) {
                    allowPass = false;
                }
            }
        }


    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
