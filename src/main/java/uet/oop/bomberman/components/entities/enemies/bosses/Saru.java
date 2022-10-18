package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.CharacterStatus;
import uet.oop.bomberman.config.Direction;

import java.util.HashMap;
import java.util.Map;

public class Saru extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();
    private Direction currentDirection;
    private CharacterStatus saruStatus;

    public Saru(double x, double y) {
        super(x, y);

        animationDict.put("down",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 5, 48,
                        21, 18, 21 * 1.5f, 18 * 1.5f, 5f, false));

        animationDict.put("up",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 4, 95,
                        21, 18, 21 * 1.5f, 18 * 1.5f, 6f, false));

        animationDict.put("right",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 3, 71,
                        21, 18, 21 * 1.6f, 18 * 1.6f, 9f, false));

        animationDict.put("left",
                new Animation(SpriteSheet.saru, 2, 2, 1000, 3, 71,
                        21, 18, 21 * 1.8f, 18 * 1.8f, 9f, true));

        animationDict.put("idle",
                new Animation(SpriteSheet.saru, 4, 4, 1000, 3, 3,
                        24, 19, 24 * 1.5f, 19 * 1.5f, 3f, false));

        animationDict.put("death",
                new Animation(SpriteSheet.saru, 4, 4, 1000, 3, 3,
                        24, 19, 24 * 1.5f, 19 * 1.5f, 3f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("idle").setLoop(true);
        animationDict.get("death").setLoop(false);

        initDirectionList();
        saruStatus = CharacterStatus.IDLE;
        currentDirection = Direction.DOWN;
        score = 5000;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (saruStatus == CharacterStatus.IDLE) {
                animationDict.get("idle").render(gc, x - camera.getX(), y - camera.getY());
            } else {
                animationDict.get(currentDirection.label).render(gc, x - camera.getX(), y - camera.getY());
            }
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
        if (destroyed) {
            animationDict.get("death").update();
            return;
        }

        if (saruStatus == CharacterStatus.IDLE) {
            animationDict.get("idle").update();
        } else {
            move();
            animationDict.get(currentDirection.label).update();
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
