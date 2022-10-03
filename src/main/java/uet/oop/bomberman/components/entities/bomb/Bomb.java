package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.entities.stillobjects.Brick;
import uet.oop.bomberman.components.entities.stillobjects.Portal;
import uet.oop.bomberman.components.entities.stillobjects.Wall;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timer;
import uet.oop.bomberman.sound.Sound;

import java.util.LinkedList;

public class Bomb extends Entity {
    public static boolean INIT = false;
    private Image image;
    public static LinkedList<Image> bombs;
    public static LinkedList<Flame> flameList = new LinkedList<>();
    private boolean allowPass;  // cho phép bomber vượt qua
    private boolean explode = false;
    private Bomber bomber;
    private int flameLength = 1;
    private int timeBeforeExplode = 1500;
    private double flameTime = 1000;
    private double time = 0;

    public static Sound soundPlaceBomb;
    public static Sound soundExplode;

    /**
     * initialization image and sound Bomb.
     */
    public static void init() {
        if (!INIT) {
            soundPlaceBomb = new Sound(Sound.PLACE_BOMB_SOUND);
            soundExplode = new Sound(Sound.EXPLODE_SOUND);
            SpriteSheet tiles = new SpriteSheet("src/main/resources/textures/classic.png", 256, 256);
            bombs.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 15, 15).getFxImage());
            INIT = true;
        }
    }

    /**
     * Create bomb.
     */
    public Bomb(int x, int y, int flameLength, Bomber bomber) {
        super(x, y);
        this.flameLength = flameLength;
        this.bomber = bomber;
    }

    public int getFlameLength() {
        return flameLength;
    }

    public void setFlameLength(int flameLength) {
        this.flameLength = flameLength;
    }

    public void setTimeBeforeExplode(int timeBeforeExplode) {
        this.timeBeforeExplode = timeBeforeExplode;
    }

    public int getTimeBeforeExplode() {
        return timeBeforeExplode;
    }

    @Override
    public void update() {
        time += Timer.getInstance().getDeltaTime();
        if (!explode) {
            if (allowPass) {
                double subX = bomber.getX() - getX();
                double subY = bomber.getY() - getY();
                if (subX < -20 || subX > 31 || subY > 33 || subY < -31) {
                    allowPass = false;
                }
            }
            if (time < timeBeforeExplode) {
                image = Sprite.Animation(bombs, time, timeBeforeExplode);
            } else {
                explode = true;
                explosion();
            }
            if (time > timeBeforeExplode + flameTime) {
                image = null;
            }
        }
    }

    /**
     * Kích hoạt phát nổ bom.
     */
    private void explosion() {
        flameList.add(new Flame(x, y, 4, false));
    }

    public boolean canPassThrough(Entity e) {
        if (e instanceof Brick) {
            Brick brick = (Brick) e;
            brick.setDestroyed(true);
            return false;
        }
        if (e instanceof Wall || e instanceof Portal) {
            return false;
        }

        if (e instanceof Bomb ) {
            Bomb bomb = (Bomb) e;
            bomb.setTimeBeforeExplode(100);
        }
        return true;
        // thêm phần cộng điểm , và qua các monster
    }
    @Override
    public void render(GraphicsContext gc) {
        flameList.forEach(flame -> render(gc));
    }
}
