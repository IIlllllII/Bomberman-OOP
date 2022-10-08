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
import uet.oop.bomberman.core.scenes.PlayScene;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bomb extends Entity {
    public static boolean initialized = false;
    private int level;
    private Image image;
    private static List<Image> bombs;
    private List<Flame> flameList = new ArrayList<>();
    private boolean allowPass;  // cho phép bomber vượt qua
    private boolean explode = false;
    private Bomber bomber;
    private int flameLength = 2;
    private int timeBeforeExplode = 1500;
    private final double flameTime = 1000;
    private double time = 0;
    private boolean done = false;

    public static Sound soundPlaceBomb;
    public static Sound soundExplode;

    /**
     * initialization image and sound Bomb.
     */
    public static void init() {
        if (!initialized) {
            bombs = new ArrayList<>();
//            soundPlaceBomb = new Sound(Sound.PLACE_BOMB_SOUND);
//            soundExplode = new Sound(Sound.EXPLODE_SOUND);
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
    public Bomb(double x, double y, int width, int height, int level, int flameLength, Bomber bomber) {
        super(x, y, width, height);
        this.flameLength = flameLength;
        this.bomber = bomber;
        this.level = level;
    }

    public Bomb(double x, double y, int width, int height, Bomber bomber) {
        super(x, y, width, height);
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
            }
        } else {
            explosion();
            flameList.forEach(flame -> flame.update());
        }

    }

    /**
     * Kích hoạt phát nổ bom.
     */
    private void explosion() {
        flameList.add(new Flame(x , y, width, height, 4, false));
        // check left
        int l = 1;
        for(; l <= flameLength; l++){
            if(LevelMap.getInstance().getMapHash((int)y/32 , (int) x/32 - l ) == LevelMap.getInstance().getHash("brick")){
                LevelMap.getInstance().setBrick((int)y/32 , (int) x/32 - l );
                break;
            }else if(LevelMap.getInstance().getMapHash((int)y/32 , (int) x/32 - l ) == LevelMap.getInstance().getHash("wall")){
                break;
            }
        }
        System.out.println(l);
        for(int i=1; i<l; i++){
            if(i != (l -1)){
                flameList.add(new Flame(x - 32 * i, y, width, height, 2, false));
            }else {
                flameList.add(new Flame(x - 32 * i, y, width, height, 2, true));
            }
        }

        //check right
        int r = 1;
        for(; r <= flameLength; r++){
            if(LevelMap.getInstance().getMapHash((int)y/32 , (int) x/32 + r ) == LevelMap.getInstance().getHash("brick")){
                LevelMap.getInstance().setBrick((int)y/32 , (int) x/32 + r );
                break;
            }else if(LevelMap.getInstance().getMapHash((int)y/32 , (int) x/32 + r ) == LevelMap.getInstance().getHash("wall")){
                break;
            }
        }
        for(int i=1; i<r; i++){
            if(i != (r - 1)){
                flameList.add(new Flame(x + 32 * i, y, width, height, 3, false));
            }else{
                flameList.add(new Flame(x + 32 * i, y, width, height, 3, true));
            }
        }

        // check up
        int u = 1;
        for(; u <= flameLength; u++){
            if(LevelMap.getInstance().getMapHash((int)y/32 - u , (int) x/32 ) == 3){
                LevelMap.getInstance().setBrick((int)y/32 - u , (int) x/32  );
                break;
            }
            if (LevelMap.getInstance().getMapHash((int)y/32 - u , (int) x/32 ) == 1) {
                break;
            }
        }
        for(int i=1; i<u; i++){
            if(i != (u -1)){
                flameList.add(new Flame(x , y - 32 * i, width, height, 0, false));
            }else {
                flameList.add(new Flame(x , y - 32 * i, width, height, 0, true));
            }
        }

        // check down
        int d = 1;
        for(; d <= flameLength; d++){
            if(LevelMap.getInstance().getMapHash((int)y/32 + d , (int) x/32 ) == LevelMap.getInstance().getHash("brick")){
                LevelMap.getInstance().setBrick((int)y/32 + d , (int) x/32   );
                break;
            }else if(LevelMap.getInstance().getMapHash((int)y/32 + d , (int) x/32 ) == LevelMap.getInstance().getHash("wall")){
                break;
            }
        }
        for(int i=1; i<d; i++){
            if(i != (d - 1)){
                flameList.add(new Flame(x , y + 32 * i, width, height, 1, false));
            }else {
                flameList.add(new Flame(x , y + 32 * i, width, height, 1, true));
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
        }else {
            done = true;
        }
    }

}
