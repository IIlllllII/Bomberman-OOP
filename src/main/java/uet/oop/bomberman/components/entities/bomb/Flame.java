package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.core.Timer;

import java.util.LinkedList;

public class Flame extends Entity {
    public static boolean initialized = false;
    public static LinkedList<Image> bombExploded = new LinkedList<>();
    public static LinkedList<Image> explosionVertical = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontal = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontalLeftLast = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontalRightLast = new LinkedList<>();
    public static LinkedList<Image> explosionVerticalTopLast = new LinkedList<>();
    public static LinkedList<Image> explosionVerticalDownLast = new LinkedList<>();
    private Image image;
    private final int flameDirection;           // Hướng của lửa: 0 Up, 1 Down, 2 Left, 3 Right, 4 Center
    private final boolean last;                 // Kiểm tra kết đuôi ngọn lửa
    private double time = 0;                    // Thời gian tính từ lúc lửa bắt đầu xuất hiện
    private final double flameTime = 1000;      // Thời gian lửa hiện lên

    public static void init() {
        if (!initialized) {
            SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, tiles, 16, 16).getFxImage());

            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 5, tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 5, tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 5, tiles, 16, 16).getFxImage());

            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 7, tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 8, tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 9, tiles, 16, 16).getFxImage());

            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 7, tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 8, tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 9, tiles, 16, 16).getFxImage());

            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 7, tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 8, tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 9, tiles, 16, 16).getFxImage());

            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 4, tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 4, tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 4, tiles, 16, 16).getFxImage());

            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 6, tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 6, tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 6, tiles, 16, 16).getFxImage());
            initialized = true;
        }
    }

    public Flame(double x, double y,int width, int height, int flameDirection, boolean last) {
        super(x, y, width, height);
        this.flameDirection = flameDirection;
        this.last = last;
    }


    @Override
    public void update(LevelMap levelMap) {
        time += Timer.getInstance().getDeltaTime();
        switch (flameDirection) {
            case 0:
                if (last) {
                    image = Sprite.animate(explosionVerticalTopLast, time, flameTime);
                } else {
                    image = Sprite.animate(explosionVertical, time, flameTime);
                }
                break;
            case 1:
                if (last) {
                    image = Sprite.animate(explosionVerticalDownLast, time, flameTime);
                } else {
                    image = Sprite.animate(explosionVertical, time, flameTime);
                }
                break;
            case 2:
                if (last) {
                    image = Sprite.animate(explosionHorizontalLeftLast, time, flameTime);
                } else {
                    image = Sprite.animate(explosionHorizontal, time, flameTime);
                }
                break;
            case 3:
                if (last) {
                    image = Sprite.animate(explosionHorizontalRightLast, time, flameTime);
                } else {
                    image = Sprite.animate(explosionHorizontal, time, flameTime);
                }
                break;
            case 4:
                image = Sprite.animate(bombExploded, time, flameTime);
                break;
            default:
                break;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
}
