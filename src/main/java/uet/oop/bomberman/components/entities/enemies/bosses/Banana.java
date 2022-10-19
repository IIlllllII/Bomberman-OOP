package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Banana extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();

    private Direction currentDirection;

    public Banana(double x, double y) {
        super(x, y);
        animationDict.put("down",
                new Animation(SpriteSheet.banana, 8, 8, 1000, 3, 2,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("up",
                new Animation(SpriteSheet.banana, 7, 7, 1000, 3, 164,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("left",
                new Animation(SpriteSheet.banana, 8, 8, 1000, 3, 84,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("right",
                new Animation(SpriteSheet.banana, 8, 8, 1000, 3, 84,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, true));
        animationDict.put("death",
                new Animation(SpriteSheet.banana, 8, 8, 1000, 3, 84,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("death").setLoop(false);

        initDirectionList();
        currentDirection = Direction.DOWN;
        //currentDirection = Direction.values()[(new Random()).nextInt(Direction.values().length)];
        score = 5000;
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
        } else {
            animationDict.get("death").update();
        }
    }

    @Override
    public boolean isDone() {
        return animationDict.get("death").isDone();
    }

    @Override
    protected void move() {
        int j = (int) (x / 32);
        int i = (int) (y / 32);
        if (j * 32 == x && i * 32 == y) {
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
        }
        x += moveX;
        y += moveY;
    }
}
