package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timers;

import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {
    public static final int MAX_LENGTH = 6;
    private static boolean initialized = false;
    private static final List<Image> bombExploded = new ArrayList<>();
    private static final List<Image> explosionVertical = new ArrayList<>();
    private static final List<Image> explosionHorizontal = new ArrayList<>();
    private static final List<Image> explosionHorizontalLeftLast = new ArrayList<>();
    private static final List<Image> explosionHorizontalRightLast = new ArrayList<>();
    private static final List<Image> explosionVerticalTopLast = new ArrayList<>();
    private static final List<Image> explosionVerticalDownLast = new ArrayList<>();
    private Image image;

    public enum FlameDirection {
        UP, DOWN, LEFT, RIGHT, CENTER
    }
    private final FlameDirection flameDirection;
    private final boolean last;                 // Kiểm tra kết đuôi ngọn lửa
    private double time = 0;                    // Thời gian tính từ lúc lửa bắt đầu xuất hiện
    private final double flameTime = 500;      // Thời gian lửa hiện lên

    private boolean done;

    public static void init() {
        if (!initialized) {
            SpriteSheet tiles = new SpriteSheet("/spriteSheet/bomb.png", 256, 128);
            bombExploded.add(new Sprite(24, 0, 4 * 24, tiles).getFxImage());
            bombExploded.add(new Sprite(24, 0, 3 * 24, tiles).getFxImage());
            bombExploded.add(new Sprite(24, 0, 2 * 24, tiles).getFxImage());
            bombExploded.add(new Sprite(24, 0, 1 * 24, tiles).getFxImage());

            explosionVertical.add(new Sprite(24, 5 * 24, 4 * 24, tiles).getFxImage());
            explosionVertical.add(new Sprite(24, 5 * 24, 3 * 24, tiles).getFxImage());
            explosionVertical.add(new Sprite(24, 5 * 24, 2 * 24, tiles).getFxImage());
            explosionVertical.add(new Sprite(24, 5 * 24, 1 * 24, tiles).getFxImage());

            explosionHorizontal.add(new Sprite(24, 3 * 24, 4 * 24, tiles).getFxImage());
            explosionHorizontal.add(new Sprite(24, 3 * 24, 3 * 24, tiles).getFxImage());
            explosionHorizontal.add(new Sprite(24, 3 * 24, 2 * 24, tiles).getFxImage());
            explosionHorizontal.add(new Sprite(24, 3 * 24, 1 * 24, tiles).getFxImage());

            explosionHorizontalLeftLast.add(new Sprite(24, 2 * 24, 4 * 24, tiles).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(24, 2 * 24, 3 * 24, tiles).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(24, 2 * 24, 2 * 24, tiles).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(24, 2 * 24, 1 * 24, tiles).getFxImage());

            explosionHorizontalRightLast.add(new Sprite(24, 4 * 24, 4 * 24, tiles).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(24, 4 * 24, 3 * 24, tiles).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(24, 4 * 24, 2 * 24, tiles).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(24, 4 * 24, 1 * 24, tiles).getFxImage());

            explosionVerticalTopLast.add(new Sprite(24, 6 * 24, 4 * 24, tiles).getFxImage());
            explosionVerticalTopLast.add(new Sprite(24, 6 * 24, 3 * 24, tiles).getFxImage());
            explosionVerticalTopLast.add(new Sprite(24, 6 * 24, 2 * 24, tiles).getFxImage());
            explosionVerticalTopLast.add(new Sprite(24, 6 * 24, 1 * 24, tiles).getFxImage());

            explosionVerticalDownLast.add(new Sprite(24, 8 * 24, 4 * 24, tiles).getFxImage());
            explosionVerticalDownLast.add(new Sprite(24, 8 * 24, 3 * 24, tiles).getFxImage());
            explosionVerticalDownLast.add(new Sprite(24, 8 * 24, 2 * 24, tiles).getFxImage());
            explosionVerticalDownLast.add(new Sprite(24, 8 * 24, 1 * 24, tiles).getFxImage());
            initialized = true;
        }
    }

    public Flame(double x, double y,int width, int height, FlameDirection flameDirection, boolean last) {
        super(x, y, width, height);
        this.flameDirection = flameDirection;
        this.last = last;
        done = false;
    }


    @Override
    public void update() {
        time += Timers.getInstance().getDeltaTime();
        if (time <= flameTime) {
            switch (flameDirection) {
                case UP:
                    if (last) {
                        image = Sprite.animation(explosionVerticalTopLast, time, flameTime);
                    } else {
                        image = Sprite.animation(explosionVertical, time, flameTime);
                    }
                    break;
                case DOWN:
                    if (last) {
                        image = Sprite.animation(explosionVerticalDownLast, time, flameTime);
                    } else {
                        image = Sprite.animation(explosionVertical, time, flameTime);
                    }
                    break;
                case LEFT:
                    if (last) {
                        image = Sprite.animation(explosionHorizontalLeftLast, time, flameTime);
                    } else {
                        image = Sprite.animation(explosionHorizontal, time, flameTime);
                    }
                    break;
                case RIGHT:
                    if (last) {
                        image = Sprite.animation(explosionHorizontalRightLast, time, flameTime);
                    } else {
                        image = Sprite.animation(explosionHorizontal, time, flameTime);
                    }
                    break;
                case CENTER:
                    image = Sprite.animation(bombExploded, time, flameTime);
                    break;
                default:
                    break;
            }
        } else {
            image = null;
            done = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (time <= flameTime) {
            gc.drawImage(image, x - camera.getX(), y - camera.getY(), 32, 32);
        }
    }

    public boolean isDone() {
        return done;
    }
}