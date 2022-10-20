package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.CharacterStatus;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class Saru extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();

    private final int visionRange = 3; //Cell unit.
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
                        21, 18, 21 * 1.6f, 18 * 1.6f, 9f, true));

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
        speed = 4;
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
            int centerX = (int) this.x + GameConfig.TILE_SIZE / 2;
            int centerY = (int) this.y + GameConfig.TILE_SIZE / 2;
            this.x = (double) (centerX / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            this.y = (double) (centerY / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            animationDict.get("idle").update();
        } else {
            animationDict.get(currentDirection.label).update();
        }
        move();
    }

    @Override
    public boolean isDone() {
        return animationDict.get("death").isDone();
    }

    @Override
    protected void move() {
        double bomberX = EntitiesManager.getInstance().players.get(0).getX();
        double bomberY = EntitiesManager.getInstance().players.get(0).getY();

        int centerX = (int) this.x + GameConfig.TILE_SIZE / 2;
        int centerY = (int) this.y + GameConfig.TILE_SIZE / 2;
        int cellX = centerX / GameConfig.TILE_SIZE;
        int cellY = centerY / GameConfig.TILE_SIZE;

        int cellXPlayer = (int) (bomberX / 32);
        int cellYPlayer = (int) (bomberY / 32);

        canMoveR = checkMapHash(cellY, cellX + 1);
        canMoveL = checkMapHash(cellY, cellX - 1);
        canMoveU = checkMapHash(cellY - 1, cellX);
        canMoveD = checkMapHash(cellY + 1, cellX);

//        System.out.println(cellX + " " + cellY + " " + cellXPlayer  + " " + cellYPlayer + " "
//                + canMoveR + " " + canMoveL + " " + canMoveU + " " + canMoveD);

        if (Math.abs(cellXPlayer - cellX) <= visionRange && Math.abs(cellYPlayer - cellY) <= visionRange) {
            moveX = 0;
            moveY = 0;
            saruStatus = CharacterStatus.MOVING;

            if (cellXPlayer == cellX) {
                if (cellYPlayer > cellY) {
                    //Direction = DOWN;
                } else {
                    //Direction = UP;
                }
                //Start suicide;
            }
            if (cellYPlayer == cellY) {
                if (cellXPlayer > cellX) {
                    //Direction = right;
                } else {
                    //Direction = left;
                }
                //Start suicide;
            }

            if (cellXPlayer < cellX && cellYPlayer < cellY) {
                //move right or down first
                if (canMoveR) {
                    lastDirection = Direction.RIGHT;
                } else if (canMoveD) {
                    lastDirection = Direction.DOWN;
                } else {
                    if (canMoveL) {
                        lastDirection = Direction.LEFT;
                    } else if (canMoveU) {
                        lastDirection = Direction.UP;
                    }
                }
            }
            if (cellXPlayer < cellX && cellYPlayer > cellY) {
                //move right or up first
                if (canMoveR) {
                    lastDirection = Direction.RIGHT;
                } else if (canMoveU) {
                    lastDirection = Direction.UP;
                } else {
                    if (canMoveD) {
                        lastDirection = Direction.DOWN;
                    } else if (canMoveL) {
                        lastDirection = Direction.LEFT;
                    }
                }
            }
            if (cellXPlayer > cellX && cellYPlayer < cellY) {
                //move left or down first
                if (canMoveL) {
                    lastDirection = Direction.LEFT;
                } else if (canMoveD) {
                    lastDirection = Direction.DOWN;
                } else {
                    if (canMoveU) {
                        lastDirection = Direction.UP;
                    } else if (canMoveR) {
                        lastDirection = Direction.RIGHT;
                    }
                }
            }
            if (cellXPlayer > cellX && cellYPlayer > cellY) {
                //move left or up first
                if (canMoveL) {
                    lastDirection = Direction.LEFT;
                } else if (canMoveU) {
                    lastDirection = Direction.UP;
                } else {
                    if (canMoveD) {
                        lastDirection = Direction.DOWN;
                    } else if (canMoveR) {
                        lastDirection = Direction.RIGHT;
                    }
                }
            }

            checkMove();

//            System.out.println(cellX + " " + cellY + " " + cellXPlayer  + " " + cellYPlayer + " "
//                    + canMoveD + " " + canMoveL + " " + canMoveU + " " + canMoveD);

            x += moveX;
            y += moveY;
            currentDirection = lastDirection;
        } else {
            saruStatus = CharacterStatus.IDLE;
        }
    }
}