package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.Timers;
import uet.oop.bomberman.core.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public static boolean initialized = false;
    private Image image;
    private static List<Image> bombs;
    private LevelMap levelMap = LevelMap.getInstance();
    private final List<Flame> flameList = new ArrayList<>();
    private boolean allowPass = true;  // cho phép bomber vượt qua
    private boolean explode = false;
    private static int flameLength = 1;
    private double timeBeforeExplode = 2000;
    private final double flameTime = 500;
    private boolean hasFlame = false;
    private double time = 0;
    private boolean done = false;

    /**
     * Initialize images and sound of bomb.
     */
    public static void init() {
        if (!initialized) {
            bombs = new ArrayList<>();
            SpriteSheet tiles = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
            bombs.add(new Sprite(16, 0, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 16, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 32, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 16, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 0, 3 * 16, tiles, 15, 15).getFxImage());

            initialized = true;
        }
    }

    /**
     * Create bomb.
     */
    public Bomb(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public static int getFlameLength() {
        return flameLength;
    }

    public static void setFlameLength(int flameLength) {
        Bomb.flameLength = flameLength;
    }

    public void setTimeBeforeExplode(double timeBeforeExplode) {
        this.timeBeforeExplode = timeBeforeExplode;
    }

    public double getTime() {
        return time;
    }

    public List<Flame> getFlameList() {
        return flameList;
    }

    public void setTimeExplodeBomb(double x, double y) {
        List<Bomb> bombList = EntitiesManager.getInstance().bombs;
        for(int i = 0; i < bombList.size(); i++){
            if( bombList.get(i).getX() == x && bombList.get(i).getY() == y){
                bombList.get(i).setTimeBeforeExplode(bombList.get(i).getTime() + 5);
            }
        }
    }

    @Override
    public void update() {
        time += Timers.getInstance().getDeltaTime();
        if (!explode) {
            if (allowPass) {
                double subX = EntitiesManager.getInstance().bombers.get(0).getX() - this.x;
                subX = Math.abs(subX);
                double subY = EntitiesManager.getInstance().bombers.get(0).getY() - this.y;
                subY = Math.abs(subY);
                if (subX > 20 || subY > 20) {
                    allowPass = false;
                }
            }
            if (time < timeBeforeExplode) {
                image = Sprite.animation(bombs, time, timeBeforeExplode);
            } else {
                image = null;
                explode = true;
            }
        } else {
            if (!hasFlame) {
                explosion();
                new Sound(Sound.EXPLODE_SOUND).playSound();
            }
            flameList.forEach(flame -> flame.update());
        }
    }

    public boolean isAllowPass() {
        return allowPass;
    }

    /**
     * Kích hoạt phát nổ bom.
     */
    private void explosion() {
        hasFlame = true;

        flameList.add(new Flame(x, y, width, height, Flame.FlameDirection.CENTER, false));

        // check left
        int l = 1;
        for (; l <= flameLength; l++) {
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 - l) == levelMap.getHash("brick")) {
                levelMap.destroyBrick((int) y / 32, (int) x / 32 - l);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 - l) == levelMap.getHash("bomb")) {
                setTimeExplodeBomb(x - 32 * l, y);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 - l) == levelMap.getHash("wall")) {
                break;
            }

        }
        for (int i = 1; i < l; i++) {
            if (i != (l - 1)) {
                flameList.add(new Flame(x - 32 * i, y, width, height, Flame.FlameDirection.LEFT, false));
            } else {
                flameList.add(new Flame(x - 32 * i, y, width, height, Flame.FlameDirection.LEFT, true));
            }
        }

        //check right
        int r = 1;
        for (; r <= flameLength; r++) {
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 + r) == levelMap.getHash("brick")) {
                levelMap.destroyBrick((int) y / 32, (int) x / 32 + r);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 + r) == levelMap.getHash("bomb")) {
                setTimeExplodeBomb(x + 32 * r, y);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 + r) == levelMap.getHash("wall")) {
                break;
            }
        }
        for (int i = 1; i < r; i++) {
            if (i != (r - 1)) {
                flameList.add(new Flame(x + 32 * i, y, width, height, Flame.FlameDirection.RIGHT, false));
            } else {
                flameList.add(new Flame(x + 32 * i, y, width, height, Flame.FlameDirection.RIGHT, true));
            }
        }

        // check up
        int u = 1;
        for (; u <= flameLength; u++) {
            if (levelMap.getHashAt((int) y / 32 - u, (int) x / 32) == levelMap.getHash("brick")) {
                levelMap.destroyBrick((int) y / 32 - u, (int) x / 32);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 - u, (int) x / 32) == levelMap.getHash("bomb")) {
                setTimeExplodeBomb(x, y - 32 * u);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 - u, (int) x / 32) == levelMap.getHash("wall")) {
                break;
            }
        }
        for (int i = 1; i < u; i++) {
            if (i != (u- 1)) {
                flameList.add(new Flame(x, y - 32 * i, width, height, Flame.FlameDirection.UP, false));
            } else {
                flameList.add(new Flame(x, y - 32 * i, width, height, Flame.FlameDirection.UP, true));
            }
        }

        // check down
        int d = 1;
        for (; d <= flameLength; d++) {
            if (levelMap.getHashAt((int) y / 32 + d, (int) x / 32) == levelMap.getHash("brick")) {
                levelMap.destroyBrick((int) y / 32 + d, (int) x / 32);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 + d, (int) x / 32) == levelMap.getHash("wall")) {
                setTimeExplodeBomb(x, y + 32 * d);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 + d, (int) x / 32) == levelMap.getHash("wall")) {
                break;
            }
        }
        for (int i = 1; i < d; i++) {
            if (i != (d - 1)) {
                flameList.add(new Flame(x, y + 32 * i, width, height, Flame.FlameDirection.DOWN, false));
            } else {
                flameList.add(new Flame(x, y + 32 * i, width, height, Flame.FlameDirection.DOWN, true));
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (time < timeBeforeExplode) {
            gc.drawImage(image, x - camera.getX(), y - camera.getY());
        } else if (time < timeBeforeExplode + flameTime) {
            flameList.forEach(flame -> flame.render(gc));
        } else {
            done = true;
        }
    }

}