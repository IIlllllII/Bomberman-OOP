package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.Camera;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends Enemy {
    private final int speed = 1;

    public Balloom(double x, double y) {
        super(x, y);

        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 1000, 0, 0, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 1000, 96, 0, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 192, 0, 32, 32);
        animationLeft.setLoop(true);
        animationRight.setLoop(true);

        Random r = new Random();
        lastDirection = Direction.values()[r.nextInt(Direction.values().length)];
        randomAnimation = false;

        score = 100;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (randomAnimation) {
                animationLeft.render(gc, x - camera.getX(), y - camera.getY());
            } else {
                animationRight.render(gc, x - camera.getX(), y - camera.getY());
            }
        } else {
            animationDeath.render(gc, x - camera.getX(), y - camera.getY());
            if (!animationDeath.isDone()) {
                //gc.setFont(PlayWindow.MCFONT);
                gc.fillText(" + " + score,
                        x + 16 - camera.getX(),
                        y + 20 - camera.getY() - animationDeath.getCalcTime() / 32);
            }
        }
    }

    @Override
    public void update() {
        if (!destroyed) {
            move();
            if (randomAnimation) {
                animationLeft.update();
            } else {
                animationRight.update();
            }
        } else {
            animationDeath.update();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
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

            List<Direction> check = new ArrayList<>();
            switch (lastDirection) {
                case UP: {
                    if (canMoveU) {
                        moveY = -speed;
                    } else {
                        if (canMoveD) check.add(Direction.DOWN);
                        if (canMoveR) check.add(Direction.RIGHT);
                        if (canMoveL) check.add(Direction.LEFT);
                    }
                    break;
                }
                case DOWN: {
                    if (canMoveD) {
                        moveY = speed;
                    } else {
                        if (canMoveU) check.add(Direction.UP);
                        if (canMoveR) check.add(Direction.RIGHT);
                        if (canMoveL) check.add(Direction.LEFT);
                    }
                    break;
                }
                case LEFT: {
                    if (canMoveL) {
                        moveX = -speed;
                    } else {
                        if (canMoveD) check.add(Direction.DOWN);
                        if (canMoveR) check.add(Direction.RIGHT);
                        if (canMoveU) check.add(Direction.UP);
                    }
                    break;
                }
                case RIGHT: {
                    if (canMoveR) {
                        moveX = speed;
                    } else {
                        if (canMoveD) check.add(Direction.DOWN);
                        if (canMoveU) check.add(Direction.UP);
                        if (canMoveL) check.add(Direction.LEFT);
                    }
                    break;
                }
            }
            if (moveY == 0 && moveX == 0) {
                Random r = new Random();
                if (check.size() != 0) {
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
        }
        x += moveX;
        y += moveY;
    }
}
