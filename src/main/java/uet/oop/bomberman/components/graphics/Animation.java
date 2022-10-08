package uet.oop.bomberman.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.Timer;

public class Animation {
    private final Image image;
    private final int count;
    private final int columns;
    private final float offsetX;
    private final float offsetY;
    private float sX;
    private float sY;
    private final float sWidth;
    private final float sHeight;
    //Used to keep track of how long current animation frame has been on screen
    private float calcTime;
    //Used to determine the speed of the animation,is total amount of time for the entire animation
    private float totalTime;
    //Stores how long each animation frame should be on screen
    private float timePerFrame;
    //Is set to true only if the wrap mode is once and the last frame of the animation is reached
    private boolean done;
    private boolean loop;
    // Get Delta Time between each frame
    private Timer timer;

    /**
     *
     * @param image : spriteSheet.
     * @param count : the number of frames.
     * @param columns : the number of the columns.
     * @param totalTime : (milli) total amount of time for the entire animation.
     * @param offsetX : the source rectangle's X coordinate position.
     * @param offsetY : the source rectangle's Y coordinate position.
     * @param sWidth : the source rectangle's width.
     * @param sHeight : the source rectangle's height.

     */
    public Animation(Image image, int count, int columns, float totalTime,
                     float offsetX, float offsetY, float sWidth, float sHeight) {
        this.image = image;
        this.count = count;
        this.columns = columns;
        this.totalTime = totalTime;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        timer = Timer.getInstance();
        timePerFrame = totalTime / count;
        reset();
    }

    public void reset() {
        sX = offsetX;
        sY = offsetY;
        done = false;
        calcTime = 0.0f;
        loop = false;
    }

    /**
     *
     * @param gc : graphics
     * @param dX : the destination rectangle's X coordinate position.
     * @param dY : the destination rectangle's Y coordinate position
     */
    public void render(GraphicsContext gc, double dX, double dY) {
        if (!done) {
            gc.drawImage(image, sX, sY, sWidth, sHeight, dX, dY,
                    GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
        }
    }

    public void update() {
        if (!done) {
            calcTime += timer.getDeltaTime();
            if (calcTime >= totalTime) {
                if (loop) {
                    calcTime = 0.0f;
                } else {
                    done = true;
                    // stop at last animation then say that the animation is done
                    calcTime = totalTime - timePerFrame;
                }
            }
            int index = (int) (calcTime / timePerFrame);
            sX = (index % columns) * sWidth  + offsetX;
            sY = ((int) (index / columns)) * sHeight + offsetY;
        }
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
