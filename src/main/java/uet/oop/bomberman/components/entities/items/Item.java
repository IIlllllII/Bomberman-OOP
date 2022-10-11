package uet.oop.bomberman.components.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timer;

public abstract class Item extends Entity {
    private static boolean initialized = false;
    protected static Image powerupBombs;
    protected static Image powerupFlames;
    protected static Image powerupSpeed;
    protected static Image powerupBrickpass;
    protected static Image powerupLives;
    protected static Image powerupBombpass;
    protected static Image powerupFlamepass;
    protected Image image;
    protected double timeAppear = 5000;
    protected double timePowerUp = 30000;
    protected boolean appear = false;
    protected boolean eaten = false;

    protected boolean done = false;
    protected double time = 0;


    public Item(double x, double y, Image image) {
        super(x, y);
        this.image = image;
    }

    public static void init() {
        if (!initialized) {
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

    public boolean isAppear() {
        return appear;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isEaten() {
        return eaten;
    }

    public abstract void changePower();

    @Override
    public void update() {
        if (appear) {
            time += Timer.getInstance().getDeltaTime();
            if (time >= timeAppear || eaten) {
                appear = false;
                time = 0;
                if (time >= timeAppear) {
                    done = true;
                }
            }
        }
        if (eaten && !done) {
            time += Timer.getInstance().getDeltaTime();
            changePower();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (appear) {
            gc.drawImage(image, x - camera.getX(), y - camera.getY());
        }
    }
}
