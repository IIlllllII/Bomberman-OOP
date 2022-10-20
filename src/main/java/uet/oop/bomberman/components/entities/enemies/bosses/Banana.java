package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.BoxCollider;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

import java.util.HashMap;
import java.util.Map;

public class Banana extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();

    private Direction currentDirection;

    private final BoxCollider boxCollider;

    private int lives;

    public Banana(double x, double y) {
        super(x, y);
        this.setWidth((int) (47 * 1.5));
        this.setHeight((int) (74 * 1.5));

        boxCollider = new BoxCollider(0, 0,15, 20);
        updateBoxCollider();

        animationDict.put("down",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 5,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("up",
                new Animation(SpriteSheet.normalBanana, 7, 7, 1000, 3, 164,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("left",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 84,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("right",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 247,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));

        animationDict.put("death",
                new Animation(SpriteSheet.deadBanana, 24, 24, 3000, 0, 0,
                76, 89, 76 * 1.5f, 89 * 1.5f, 3.3f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("death").setLoop(false);

        initDirectionList();
        currentDirection = lastDirection = Direction.values()[r.nextInt(Direction.values().length)];
        score = 2000;
    }

    public BoxCollider getBoxCollider() {
        return boxCollider;
    }

    public void decreaseLives() {
        lives--;
        if (lives <= 0) {
            setDestroyed(true);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            animationDict.get(currentDirection.label)
                    .render(gc, x - camera.getX(), y - camera.getY());
        } else {
            animationDict.get("death").render(gc, x - camera.getX(), y - camera.getY());
            if (! animationDict.get("death").isDone()) {
                gc.fillText(" + " + score,
                        x - camera.getX() + 16,
                        y - camera.getY() + 20 - animationDict.get("death").getCalcTime() / 32);
            } else {
                done = true;
            }
        }
    }

    @Override
    public void update() {
        if (!destroyed) {
            move();
            animationDict.get(currentDirection.label).update();
            updateBoxCollider();
        } else {
            animationDict.get("death").update();
        }
    }

    private void updateBoxCollider() {
        boxCollider.setLocation(
                this.x + (this.width - boxCollider.getWidth()) / 2.0,
                this.y + (this.height - boxCollider.getHeight()) / 2.0 + 10
        );
    }

    @Override
    public boolean isDone() {
        return animationDict.get("death").isDone();
    }

    @Override
    protected void move() {
        int j = (int) (boxCollider.getX() / 32);
        int i = (int) (boxCollider.getY() / 32);

//        System.out.println("i & j: " + i + " " + j);
//        System.out.println(j * GameConfig.TILE_SIZE);
//        System.out.println(boxCollider.getX());

        //if (j * 32 == boxCollider.getX() && i * 32 == boxCollider.getY()) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            checkMove();
            if (moveY == 0 && moveX == 0) {
                if (directionList.size() != 0) {
                    int ran = r.nextInt(directionList.size());
                    if (directionList.get(ran) == Direction.UP) {
                        lastDirection = Direction.UP;
                    }
                    if (directionList.get(ran) == Direction.DOWN) {
                        lastDirection = Direction.DOWN;
                    }
                    if (directionList.get(ran) == Direction.RIGHT) {
                        lastDirection = Direction.RIGHT;
                        //randomAnimation = false;
                    }
                    if (directionList.get(ran) == Direction.LEFT) {
                        lastDirection = Direction.LEFT;
                        //randomAnimation = true;
                    }
                    currentDirection = lastDirection;
                }
            }
        //}
        x += moveX;
        y += moveY;
    }
}
