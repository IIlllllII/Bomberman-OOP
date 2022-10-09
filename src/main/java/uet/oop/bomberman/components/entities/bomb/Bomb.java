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
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.Timer;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public static boolean initialized = false;
    private Image image;
    private static List<Image> bombs;
    private final List<Flame> flameList = new ArrayList<>();
    private boolean allowPass;  // cho phép bomber vượt qua
    private boolean explode = false;
    private final Bomber bomber;
    private int flameLength;
    private int timeBeforeExplode = 1500;
    private final double flameTime = 1000;
    private boolean hasFlame = false;
    private double time = 0;
    private boolean done = false;
    private boolean hasFlame = false;

    /**
     * Initialize images and sound of bomb.
     */
    public static void init() {
        if (!initialized) {
            bombs = new ArrayList<>();
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            bombs.add(new Sprite(16, 0, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 16, 3 * 16, tiles, 15, 15).getFxImage());
            bombs.add(new Sprite(16, 32, 3 * 16, tiles, 15, 15).getFxImage());
            initialized = true;
        }
    }

    /**
     * Create bomb.
     */
    public Bomb(int x, int y, int width, int height, int flameLength, Bomber bomber) {
        super(x, y, width, height);
        this.flameLength = flameLength;
        this.bomber = bomber;
    }

    public Bomb(double x, double y, int width, int height, Bomber bomber) {
        super(x, y, width, height);
        this.flameLength = 2;
        this.bomber = bomber;
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
//            if (allowPass) {
//                double subX = bomber.getX() - getX();
//                double subY = bomber.getY() - getY();
//                if (subX < -20 || subX > 31 || subY > 33 || subY < -31) {
//                    allowPass = false;
//                }
//            }
            if (time < timeBeforeExplode) {
                image = Sprite.animate(bombs, time, timeBeforeExplode);
            } else {
                image = null;
                explode = true;
                new Sound(Sound.EXPLODE_SOUND).playSound();
            }
        } else {
<<<<<<< HEAD
            if(!hasFlame){
                explosion();
                hasFlame = true;
            }
            flameList.forEach(flame -> flame.update());
=======
            if (! hasFlame) {
                explosion();
            }
            flameList.forEach(Flame::update);
>>>>>>> 8a2c70317b01548df7fdca62a25a5ee51a9b3ed1
        }
    }

    /**
     * Kích hoạt phát nổ bom.
     */
    private void explosion() {
        hasFlame = true;

        flameList.add(new Flame(x , y, width, height, Flame.FlameDirection.CENTER, false));
        LevelMap levelMap = LevelMap.getInstance();

        // check left
        int l = 1;
        for (; l <= flameLength; l++) {
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 - l) == levelMap.getHash("brick")) {
                levelMap.setBrick((int) y / 32, (int) x / 32 - l);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 - l) == levelMap.getHash("wall")) {
                l--;
                break;
            }
            if (l == flameLength) {
                break;
            }
        }
        for (int i = 1; i <= l; i++) {
            if (i != l) {
                flameList.add(new Flame(x - 32 * i, y, width, height, Flame.FlameDirection.LEFT, false));
            } else {
                flameList.add(new Flame(x - 32 * i, y, width, height, Flame.FlameDirection.LEFT, true));
            }
        }

        //check right
        int r = 1;
        for (; r <= flameLength; r++) {
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 + r) == levelMap.getHash("brick")) {
                levelMap.setBrick((int) y / 32, (int) x / 32 + r);
                break;
            }
            if (levelMap.getHashAt((int) y / 32, (int) x / 32 + r) == levelMap.getHash("wall")) {
                r--;
                break;
            }
            if (r == flameLength) {
                break;
            }
        }
        for (int i = 1; i <= r; i++) {
            if (i != r) {
                flameList.add(new Flame(x + 32 * i, y, width, height, Flame.FlameDirection.RIGHT, false));
            } else {
                flameList.add(new Flame(x + 32 * i, y, width, height, Flame.FlameDirection.RIGHT, true));
            }
        }

        // check up
        int u = 1;
        for (; u <= flameLength; u++) {
            if (levelMap.getHashAt((int) y / 32 - u, (int) x / 32 ) == levelMap.getHash("brick")) {
                levelMap.setBrick((int) y / 32 - u, (int) x / 32);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 - u, (int) x / 32) == levelMap.getHash("wall")) {
                u--;
                break;
            }
            if (u == flameLength) {
                break;
            }
        }
        for (int i = 1; i <= u; i++) {
            if (i != u) {
                flameList.add(new Flame(x , y - 32 * i, width, height, Flame.FlameDirection.UP, false));
            } else {
                flameList.add(new Flame(x , y - 32 * i, width, height, Flame.FlameDirection.UP, true));
            }
        }

        // check down
        int d = 1;
        for (; d <= flameLength; d++) {
            if (levelMap.getHashAt((int) y /32 + d , (int) x / 32) == levelMap.getHash("brick")) {
                levelMap.setBrick((int) y / 32 + d , (int) x / 32);
                break;
            }
            if (levelMap.getHashAt((int) y / 32 + d , (int) x / 32) == levelMap.getHash("wall")) {
                d--;
                break;
            }
            if (d == flameLength) {
                break;
            }
        }
        for (int i = 1; i <= d; i++) {
            if (i != d) {
                flameList.add(new Flame(x , y + 32 * i, width, height, Flame.FlameDirection.DOWN, false));
            } else {
                flameList.add(new Flame(x , y + 32 * i, width, height, Flame.FlameDirection.DOWN, true));
            }
        }
        // check vị trí của bomber
        //check vị trí của monster ....
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

        if (e instanceof Bomb) {
            Bomb bomb = (Bomb) e;
            bomb.setTimeBeforeExplode(100);
        }
        return true;
        //TODO: thêm phần cộng điểm, và qua các monster
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
