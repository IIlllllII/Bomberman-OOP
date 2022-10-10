package uet.oop.bomberman.components.entities.powerUp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timer;

public abstract class PowerUp extends Entity {
    private static boolean initialized = false;
    public static Image powerupBombs ;
    public static Image powerupFlames ;
    public static Image powerupSpeed ;
    public static Image powerupBrickpass ;
    public static Image powerupLives ;
    public static Image powerupBombpass ;
    public static Image powerupFlamepass ;
    protected Image image;
    protected double timeAppear = 5000;
    protected double timePowerUp = 30000;
    protected boolean appear = false;
    protected boolean eaten = false;
    protected double time = 0;


    public PowerUp(double x, double y, Image image){
        super(x, y);
        this.image = image;
    }

    public static void init(){
        if(!initialized){
            SpriteSheet item = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
            powerupBombs = new Sprite(16, 0 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupFlames = new Sprite(16, 1 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupSpeed = new Sprite(16, 2 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupBrickpass = new Sprite(16, 3 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupLives = new Sprite(16, 4 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupBombpass = new Sprite(16, 5 * 16, 10 * 16, item, 16, 16).getFxImage();
            powerupFlamepass = new Sprite(16, 6 * 16, 10 * 16, item, 16, 16).getFxImage();
            initialized = true;
        }
    }

    public void setAppear(boolean appear) {
        this.appear = appear;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x - camera.getX(), y - camera.getY());
    }
}
