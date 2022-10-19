package uet.oop.bomberman.components.entities.enemies.normal;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

public class Ovape extends Enemy {
    private boolean checkWall = false;
    private Animation animationPassWallLeft;
    private Animation animationPassWallRight;

    public Ovape(double x, double y) {
        super(x, y);

        animationLeft = new Animation(SpriteSheet.enemy, 2, 2, 1000, 0, 7 * 32, 32, 32);
        animationPassWallLeft = new Animation(SpriteSheet.enemy, 2, 2, 1000, 2 * 32, 7 * 32, 32, 32);
        animationRight= new Animation(SpriteSheet.enemy, 2, 2, 1000, 4 * 32, 7 * 32, 32, 32);
        animationPassWallRight = new Animation(SpriteSheet.enemy, 2, 2, 1000, 6 * 32, 7 * 32, 32, 32);
        animationDeath = new Animation(SpriteSheet.enemy, 4, 4, 1000, 8 * 32, 7 * 32, 32, 32);

        animationLeft.setLoop(true);
        animationRight.setLoop(true);
        animationPassWallRight.setLoop(true);
        animationPassWallLeft.setLoop(true);
        initDirectionList();
        lastDirection = directionList.get(r.nextInt(directionList.size()));

        score = 2000;
    }

    @Override
    protected void move() {
        LevelMap levelMap = LevelMap.getInstance();
        int j = (int) (x / GameConfig.TILE_SIZE);
        int i = (int) (y / GameConfig.TILE_SIZE);
        if(levelMap.getHashAt(i, j) == levelMap.getHash("wall")){
            checkWall = true;
        } else {
            checkWall = false;
        }

        if (j * GameConfig.TILE_SIZE == x && i * GameConfig.TILE_SIZE == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            checkMove();
            if (directionList.size() != 0) {
                int ran = r.nextInt(directionList.size());
                lastDirection = directionList.get(ran);
            }
        }
        x += moveX;
        y += moveY;
    }

    @Override
    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / GameConfig.TILE_SIZE) - 1
                || j < 0 || j > (levelMap.getWidth() / GameConfig.TILE_SIZE) - 1) {
            return false;
        }
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (randomAnimation) {
                if (checkWall) {
                    animationPassWallLeft.render(gc, x - camera.getX(), y - camera.getY());
                } else {
                    animationLeft.render(gc, x - camera.getX(), y - camera.getY());
                }
            } else {
                if (checkWall) {
                    animationPassWallRight.render(gc, x - camera.getX(), y - camera.getY());
                } else {
                    animationRight.render(gc, x - camera.getX(), y - camera.getY());
                }
            }
        } else {
            animationDeath.render(gc, x - camera.getX(), y - camera.getY());
            if (!animationDeath.isDone()) {
                gc.fillText(" + " + score, x + 16, y + 20 - animationDeath.getCalcTime() / 32);
            } else {
                done = true;
            }
        }
    }

    @Override
    public void update() {
        if (!destroyed) {
            move();
            if (randomAnimation) {
                if (checkWall) {
                    animationPassWallLeft.update();
                } else {
                    animationLeft.update();
                }
            } else {
                if (checkWall) {
                    animationPassWallRight.update();
                } else {
                    animationRight.update();
                }
            }
        } else {
            animationDeath.update();
        }
    }
}
