package uet.oop.bomberman.components.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.items.item_types.Coin;
import uet.oop.bomberman.components.entities.items.item_types.Invincible;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timers;
import uet.oop.bomberman.core.sound.Sound;

public abstract class Item extends Entity {
    private static boolean initialized = false;
    public static Image bombUp;
    public static Image fireUp;
    public static Image speedUp;
    public static Image livesUp;
    protected static Image brickPass;
    protected static Image bombPass;
    protected static Image flamePass;
    protected static Image invincible;
    protected static Image coinImage;

    protected Image image;
    protected double timeAppear = 10000;
    protected double timePowerUp = 0.0;
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
            bombUp = new Sprite(16, 0 * 16, 10 * 16, item).getFxImage();
            fireUp = new Sprite(16, 1 * 16, 10 * 16, item).getFxImage();
            speedUp = new Sprite(16, 2 * 16, 10 * 16, item).getFxImage();
            brickPass = new Sprite(16, 3 * 16, 10 * 16, item).getFxImage();
            livesUp = new Sprite(16, 4 * 16, 10 * 16, item).getFxImage();
            bombPass = new Sprite(16, 5 * 16, 10 * 16, item).getFxImage();
            flamePass = new Sprite(16, 6 * 16, 10 * 16, item).getFxImage();
            invincible = new Sprite(16, 7 * 16, 10 * 16, item).getFxImage();
            coinImage = SpriteSheet.coin;
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
        if (eaten && !(this instanceof Coin) && !(this instanceof Invincible)) {
            new Sound(Sound.EAT_ITEM).play();
        }
    }

    public boolean isEaten() {
        return eaten;
    }

    public abstract void changePower();
    public abstract Image getImage();

    public void setTimePowerUp(double timePowerUp) {
        this.timePowerUp = timePowerUp;
    }

    public boolean isDone() {
        return done;
    }

    /**
     *
     * @return time in millis.
     */
    public double getTimePowerUp() {
        return timePowerUp;
    }

    @Override
    public void update() {
        if (appear) {
            time += Timers.getInstance().getDeltaTime();
            if (time >= timeAppear || eaten) {
                appear = false;
                time = 0;
                if (time >= timeAppear) {
                    done = true;
                }
            }
        }
        if (eaten && !done) {
            time += Timers.getInstance().getDeltaTime();
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
