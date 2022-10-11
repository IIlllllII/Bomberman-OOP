package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends Entity {
    private Animation balloomLeft;
    private Animation balloomRight;
    private Animation balloomDeath;
    private final double speed = 1;
    private boolean destroyed;
    private double moveX;
    private double moveY;
    private boolean canMoveR;
    private boolean canMoveL;
    private boolean canMoveU;
    private boolean canMoveD;
    private Direction lastDirection;
    private boolean randomAnimation;

    public Balloom(double x, double y) {
        this.x = x;
        this.y = y;

        balloomRight = new Animation(SpriteSheet.NES, 3, 3, 1000, 0, 240, 16, 16);
        balloomLeft = new Animation(SpriteSheet.NES, 3, 3, 1000, 48, 240, 16, 16);
        balloomDeath = new Animation(SpriteSheet.NES, 5, 5, 1000, 96, 240, 16, 16);
        balloomLeft.setLoop(true);
        balloomRight.setLoop(true);

        destroyed = false;
        moveX = 0;
        moveY = 0;
        canMoveD = false;
        canMoveU = false;
        canMoveL = false;
        canMoveR = false;

        Random r = new Random();
        lastDirection = Direction.values()[r.nextInt(Direction.values().length)];
        randomAnimation = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (randomAnimation) {
                balloomLeft.render(gc, x - camera.getX(), y - camera.getY());
            } else {
                balloomRight.render(gc, x - camera.getX(), y - camera.getY());
            }
        } else {
            balloomDeath.render(gc, x - camera.getX(), y - camera.getY());
            if (!balloomDeath.isDone()) {
                //gc.setFont(PlayWindow.MCFONT);
                gc.fillText(" + 200", x + 16, y + 20 - balloomDeath.getCalcTime() / 16);
            }
        }
    }

    @Override
    public void update() {
        if (!destroyed) {
            moveBalloom();
            if (randomAnimation) {
                balloomLeft.update();
            } else {
                balloomRight.update();
            }
        } else {
            balloomDeath.update();
        }
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        // plus score ...
    }

    private void moveBalloom() {
        int j = (int) (x / 32);
        int i = (int) (y / 32);
        if (j * 32 == x && i * 32 == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);
            List<Direction> check = new ArrayList<>();
            if (canMoveR) {
                if (lastDirection == Direction.RIGHT) {
                    check.add(Direction.RIGHT);
                    check.add(Direction.RIGHT);
                    check.remove(Direction.LEFT);
                }
                check.add(Direction.RIGHT);
            }
            if (canMoveL) {
                if (lastDirection == Direction.LEFT) {
                    check.add(Direction.LEFT);
                    check.add(Direction.LEFT);
                    check.remove(Direction.RIGHT);
                }
                check.add(Direction.LEFT);
            }
            if (canMoveU) {
                if (lastDirection == Direction.UP) {
                    check.add(Direction.UP);
                    check.add(Direction.UP);
                    check.remove(Direction.DOWN);
                }
                check.add(Direction.UP);
            }
            if (canMoveD) {
                if (lastDirection == Direction.DOWN) {
                    check.add(Direction.DOWN);
                    check.add(Direction.DOWN);
                    check.remove(Direction.UP);
                }
                check.add(Direction.DOWN);
            }
            if (check.size() > 0) {
                Random r = new Random();
                int ran = r.nextInt(check.size());
                if (check.get(ran) == Direction.UP) {
                    moveY = -speed;
                    lastDirection = Direction.UP;
                }
                if (check.get(ran) == Direction.DOWN) {
                    moveY = speed;
                    lastDirection = Direction.DOWN;
                }
                if (check.get(ran) == Direction.RIGHT) {
                    moveX = speed;
                    if (lastDirection != Direction.RIGHT) {
                        lastDirection = Direction.RIGHT;
                        randomAnimation = !randomAnimation;
                    }
                }
                if (check.get(ran) == Direction.LEFT) {
                    moveX = -speed;
                    if (lastDirection != Direction.LEFT) {
                        lastDirection = Direction.LEFT;
                        randomAnimation = !randomAnimation;
                    }
                }
            }
        }
        x += moveX;
        y += moveY;
        x = round(x * 10) / 10;
        y = round(y * 10) / 10;
    }

    private boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / 32) - 1 || j < 0 || j > (levelMap.getWidth() / 32) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass");
    }

    private double round(double a) {
        double num = (int) (a);
        if (num + 0.5 <= a) {
            num += 1;
        }
        return num;
    }
}
