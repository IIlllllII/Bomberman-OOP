package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

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

        score = 2000;
    }

    @Override
    protected void move() {
        LevelMap levelMap = LevelMap.getInstance();
        int j = (int) (x / 32);
        int i = (int) (y / 32);
        if(levelMap.getHashAt(i, j) == levelMap.getHash("wall")){
            checkWall = true;
        } else {
            checkWall = false;
        }

        if (j * 32 == x && i * 32 == y) {
            moveX = 0;
            moveY = 0;
            canMoveR = checkMapHash(i, j + 1);
            canMoveL = checkMapHash(i, j - 1);
            canMoveU = checkMapHash(i - 1, j);
            canMoveD = checkMapHash(i + 1, j);

            checkMove();
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
                    randomAnimation = false;
                }
                if (directionList.get(ran) == Direction.LEFT) {
                    lastDirection = Direction.LEFT;
                    randomAnimation = true;
                }
            }
        }
        x += moveX;
        y += moveY;
    }

    @Override
    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / 32) - 1 || j < 0 || j > (levelMap.getWidth() / 32) - 1) {
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
