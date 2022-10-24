package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    private int lives;
    private CharacterStatus saruStatus;

    private boolean suicide;

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
                new Animation(SpriteSheet.saru, 4, 4, 1000, 57, 71,
                        23, 19, 23 * 1.5f, 19 * 1.5f, 4f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("idle").setLoop(true);
        animationDict.get("death").setLoop(false);

        initDirectionList();
        saruStatus = CharacterStatus.IDLE;
        lastDirection = Direction.values()[r.nextInt(Direction.values().length)];
        score = 3000;
        speed = 4;
        lives = 3;
        suicide = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            if (saruStatus == CharacterStatus.IDLE) {
                animationDict.get("idle").render(gc, x - camera.getX(), y - camera.getY());
            } else {
                animationDict.get(lastDirection.label).render(gc, x - camera.getX(), y - camera.getY());
            }
        } else {
            animationDict.get("death").render(gc, x - camera.getX(), y - camera.getY());
            if (! animationDict.get("death").isDone()) {
                Text text = new Text();
                gc.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                gc.setFill(Color.SNOW);
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
            animationDict.get(lastDirection.label).update();
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
        int bomberWidth = EntitiesManager.getInstance().players.get(0).getWidth();
        int bomberHeight = EntitiesManager.getInstance().players.get(0).getHeight();
        int cellXPlayer = (int) ((bomberX + bomberWidth / 2) / GameConfig.TILE_SIZE);
        int cellYPlayer = (int) ((bomberY + bomberHeight / 2) / GameConfig.TILE_SIZE);

        int centerX = (int) this.x + GameConfig.TILE_SIZE / 2;
        int centerY = (int) this.y + GameConfig.TILE_SIZE / 2;
        int cellX = centerX / GameConfig.TILE_SIZE;
        int cellY = centerY / GameConfig.TILE_SIZE;


        canMoveR = checkMapHash(cellY, cellX + 1);
        canMoveL = checkMapHash(cellY, cellX - 1);
        canMoveU = checkMapHash(cellY - 1, cellX);
        canMoveD = checkMapHash(cellY + 1, cellX);


        if (suicide) {
            saruStatus = CharacterStatus.MOVING;
            checkMove();
            x += moveX;
            y += moveY;
            if (cellX == cellXPlayer && cellY == cellYPlayer) {
                suicide = false;
                saruStatus = CharacterStatus.IDLE;
            }
            return;
        }

        if (Math.abs(cellXPlayer - cellX) <= visionRange && Math.abs(cellYPlayer - cellY) <= visionRange
            && EntitiesManager.getInstance().players.get(0).getPlayerStatus() != CharacterStatus.DEAD) {
            moveX = 0;
            moveY = 0;

            if (cellXPlayer == cellX) {
                if (cellYPlayer > cellY) {
                    lastDirection = Direction.DOWN;
                } else {
                    lastDirection = Direction.UP;
                }
                suicide = true;
                saruStatus = CharacterStatus.MOVING;
                if (lastDirection == Direction.UP && !canMoveU || lastDirection == Direction.DOWN && !canMoveD) {
                    suicide = false;
                    saruStatus = CharacterStatus.IDLE;
                }

            }
            if (cellYPlayer == cellY) {
                if (cellXPlayer > cellX) {
                    lastDirection = Direction.RIGHT;
                } else {
                    lastDirection = Direction.LEFT;
                }
                suicide = true;
                saruStatus = CharacterStatus.MOVING;
                if (lastDirection == Direction.RIGHT && !canMoveR || lastDirection == Direction.LEFT && !canMoveL) {
                    suicide = false;
                    saruStatus = CharacterStatus.IDLE;
                }
            }
            //System.out.println("In dangerous range: suicide: " + suicide + ", direction: " + lastDirection);
        } else {
            saruStatus = CharacterStatus.IDLE;
        }
    }
}
