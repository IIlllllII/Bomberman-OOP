package uet.oop.bomberman.components.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.core.Timer;

import java.util.LinkedList;
import java.util.Map;

public class Flame extends Entity {
    public static boolean INIT = false;
    public static LinkedList<Image> bombExploded = new LinkedList<>();
    public static LinkedList<Image> explosionVertical = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontal = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontalLeftLast = new LinkedList<>();
    public static LinkedList<Image> explosionHorizontalRightLast = new LinkedList<>();
    public static LinkedList<Image> explosionVerticalTopLast = new LinkedList<>();
    public static LinkedList<Image> explosionVerticalDownLast = new LinkedList<>();
    private Image image;
    private int direct;// Hướng của lửa: 0 Up, 1 Down, 2 Left, 3 Right, 4 Center
    private boolean last; // Kiểm tra kết đuôi ngọn lửa
    private double time = 0; // Thời gian tính từ lúc lửa bắt đầu xuất hiện
    private double flameTime = 1000;// Thời gian lửa hiện lên

    public static void init() {
        if (!INIT) {
            SpriteSheet tiles = new SpriteSheet("src/main/resources/textures/classic.png", 256, 256);
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 4, SpriteSheet.tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 5, SpriteSheet.tiles, 16, 16).getFxImage());
            bombExploded.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 6, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 5, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 5, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVertical.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 5, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 7, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 8, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontal.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 9, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 7, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 8, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalLeftLast.add(new Sprite(Sprite.DEFAULT_SIZE, 0, 9, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 7, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 8, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionHorizontalRightLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 9, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 4, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 4, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalTopLast.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 4, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 1, 6, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 2, 6, SpriteSheet.tiles, 16, 16).getFxImage());
            explosionVerticalDownLast.add(new Sprite(Sprite.DEFAULT_SIZE, 3, 6, SpriteSheet.tiles, 16, 16).getFxImage());
            INIT = true;
        }
    }

    public Flame(int x, int y, int direct, boolean last) {
        super(x, y);
        this.direct = direct;
        this.last = last;
    }


    @Override
    public void update() {
        time += Timer.getInstance().getDeltaTime();
        switch (direct){
            case 0:
                if(last) {
                    image = Sprite.Animation(explosionVerticalTopLast, time, flameTime);
                }else {
                    image = Sprite.Animation(explosionVertical, time, flameTime);
                }
                break;
            case 1:
                if(last) {
                    image = Sprite.Animation(explosionVerticalDownLast, time, flameTime);
                }else {
                    image = Sprite.Animation(explosionVertical, time, flameTime);
                }
                break;
            case 2:
                if(last) {
                    image = Sprite.Animation(explosionHorizontalLeftLast, time, flameTime);
                }else {
                    image = Sprite.Animation(explosionHorizontal, time, flameTime);
                }
                break;
            case 3:
                if(last) {
                    image = Sprite.Animation(explosionHorizontalRightLast, time, flameTime);
                }else {
                    image = Sprite.Animation(explosionHorizontal, time, flameTime);
                }
                break;
            case 4:
                image = Sprite.Animation(bombExploded, time, flameTime);
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
