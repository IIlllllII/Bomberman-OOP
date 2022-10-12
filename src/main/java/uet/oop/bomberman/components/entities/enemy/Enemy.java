package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends Entity {
    protected Animation animationLeft;
    protected Animation animationRight;
    protected Animation animationDeath;
    protected boolean destroyed = false;
    protected boolean done = false;
    protected double moveX = 0;
    protected double moveY = 0;
    protected boolean canMoveR = false;
    protected boolean canMoveL = false;
    protected boolean canMoveU = false;
    protected boolean canMoveD = false;
    Random r = new Random();
    protected Direction lastDirection = Direction.values()[r.nextInt(Direction.values().length)];;

    protected List<Direction> directionList = new ArrayList<>();
    protected boolean randomAnimation = false; // left or right
    protected int speed = 1;
    protected int score;

    public Enemy(double x, double y) {
        super(x, y);
    }

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
            } else {
                done = true;
            }
        }
    }

    public void update(){
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

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        // plus score ...
    }

    public boolean isDone() {
        return animationDeath.isDone();
    }

    protected abstract void move();

    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / 32) - 1 || j < 0 || j > (levelMap.getWidth() / 32) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass");
    }

    protected void initDirectionList(){
        directionList.clear();
        directionList.add(Direction.UP);
        directionList.add(Direction.DOWN);
        directionList.add(Direction.RIGHT);
        directionList.add(Direction.LEFT);
    }

    protected void checkMove(){
        switch (lastDirection) {
            case UP: {
                if (canMoveU) {
                    moveY = -speed;
                    initDirectionList();
                } else {
                    directionList.remove(Direction.UP);
                }
                break;
            }
            case DOWN: {
                if (canMoveD) {
                    moveY = speed;
                    initDirectionList();
                } else {
                    directionList.remove(Direction.DOWN);
                }
                break;
            }
            case LEFT: {
                if (canMoveL) {
                    moveX = -speed;
                    initDirectionList();
                } else {
                    directionList.remove(Direction.LEFT);
                }
                break;
            }
            case RIGHT: {
                if (canMoveR) {
                    moveX = speed;
                    initDirectionList();
                } else {
                    directionList.remove(Direction.RIGHT);
                }
                break;
            }
        }
    }
}
