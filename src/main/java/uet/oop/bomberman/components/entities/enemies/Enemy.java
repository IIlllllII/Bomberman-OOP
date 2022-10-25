package uet.oop.bomberman.components.entities.enemies;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.components.entities.LivingEntity;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.game.TopBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends LivingEntity {
    protected Animation animationLeft;
    protected Animation animationRight;
    protected Animation animationDeath;
    protected boolean done = false;
    protected double moveX = 0;
    protected double moveY = 0;
    protected boolean canMoveR = false;
    protected boolean canMoveL = false;
    protected boolean canMoveU = false;
    protected boolean canMoveD = false;
    protected Random r = new Random();

    protected List<Direction> directionList = new ArrayList<>();
    protected boolean randomAnimation = false; // left or right
    protected int score;

    public Enemy(double x, double y) {
        super(x, y);
        speed = 1;
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
                gc.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                gc.setFill(Color.SNOW);
                gc.fillText(" + " + score,
                        x - camera.getX() + 16,
                        y - camera.getY() + 20 - animationDeath.getCalcTime() / 32);
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
        if (destroyed) {
            TopBar.getInstance().addScore(score);
        }
    }

    public boolean isDone() {
        return animationDeath.isDone();
    }

    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / GameConfig.TILE_SIZE) - 1
                || j < 0 || j > (levelMap.getWidth() / GameConfig.TILE_SIZE) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass");
    }

    protected void initDirectionList(){
        directionList.clear();
        directionList.add(Direction.UP);
        directionList.add(Direction.DOWN);
        directionList.add(Direction.LEFT);
        directionList.add(Direction.RIGHT);
    }

    protected void checkMove() {
        if (currentDirection == null) {
            initDirectionList();
            return;
        }
        switch (currentDirection) {
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
            default:
                break;
        }
    }
}
