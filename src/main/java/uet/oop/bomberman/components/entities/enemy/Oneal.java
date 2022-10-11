package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Oneal extends Enemy {
    private double speed = 1;

    public Oneal(double x, double y) {
        super(x, y);

        animationLeft = new Animation(SpriteSheet.enemy, 3, 3, 3000, 0, 32, 32, 32);
        animationRight = new Animation(SpriteSheet.enemy, 3, 3, 3000, 96, 32, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 3, 3, 1000, 192, 32, 32, 32);
        animationRight.setLoop(true);
        animationLeft.setLoop(true);

        Random r = new Random();
        lastDirection = Direction.values()[r.nextInt(Direction.values().length)];
        randomAnimation = false;

        score = 200;
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
                gc.fillText(" + " + score, x + 16, y + 20 - animationDeath.getCalcTime() / 32);
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

    @Override
    protected void move() {
        double bomberX = EntitiesManager.getInstance().players.get(0).getX();
        double bomberY = EntitiesManager.getInstance().players.get(0).getY();

        int j = (int) ((x + 12) / 32);
        int i = (int) ((y + 16) / 32);
        int jPlayer = (int) (bomberX + 16) / 32;
        int iPlayer = (int) (bomberY + 16) / 32;

        if (j * 32 == x && i * 32 == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            Random r = new Random();
            int temp = r.nextInt(3);
            speed = (temp == 0) ? 1 : temp;

            if (Math.abs(jPlayer - j) < 2 && Math.abs(iPlayer - i) < 2) {
                if (jPlayer < j && canMoveL) {
                    moveX = -speed;
                } else if (jPlayer > j && canMoveR) {
                    moveX = speed;
                } else if (iPlayer < i && canMoveU) {
                    moveY = -speed;
                } else if (iPlayer > i && canMoveD) {
                    moveY = speed;
                }
            } else {
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
