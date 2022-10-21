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
    private final double flameTime = 300;      // Thời gian lửa hiện lên

    private boolean done;

    public static void init() {
        if (!initialized) {
            SpriteSheet tiles = new SpriteSheet("/spriteSheet/classic.png", 256, 256);
            bombExploded.add(new Sprite(16, 0, 4 * 16, tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(16, 0, 5 * 16, tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(16, 0, 6 * 16, tiles, 16, 16).getFxImage());

            explosionVertical.add(new Sprite(16, 1 * 16, 5 * 16, tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(16, 2 * 16, 5 * 16, tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(16, 3 * 16, 5 * 16, tiles, 16, 16).getFxImage());

            explosionHorizontal.add(new Sprite(16, 1 * 16, 7 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(16, 1 * 16, 8 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(16, 1 * 16, 9 * 16, tiles, 16, 16).getFxImage());

            explosionHorizontalLeftLast.add(new Sprite(16, 0, 7 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(16, 0, 8 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(16, 0, 9 * 16, tiles, 16, 16).getFxImage());

            explosionHorizontalRightLast.add(new Sprite(16, 2 * 16, 7 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(16, 2 * 16, 8 * 16, tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(16, 2 * 16, 9 * 16, tiles, 16, 16).getFxImage());

            explosionVerticalTopLast.add(new Sprite(16, 1* 16, 4 * 16, tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(16, 2 * 16, 4 * 16, tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(16, 3 * 16, 4 * 16, tiles, 16, 16).getFxImage());

            explosionVerticalDownLast.add(new Sprite(16, 1* 16, 6 * 16, tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(16, 2 * 16, 6 * 16, tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(16, 3 * 16, 6 * 16, tiles, 16, 16).getFxImage());
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
            gc.drawImage(image, x - camera.getX(), y - camera.getY());
        }
    }

    public boolean isDone() {
        return done;
    }
}