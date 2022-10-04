package uet.oop.bomberman.components.entities.players;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.Killable;
import uet.oop.bomberman.components.entities.Movable;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.PlayerStatus;

import java.util.HashMap;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    public Bomber(int i, int j) {
        super(i, j);
    }

    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;

    private int lives = 3;

    private int currentSpriteIndex = 0;

    private PlayerStatus playerStatus = PlayerStatus.IDLE;
    private Direction direction = Direction.DOWN;

    public static void init() {
        if (! initialized) {
            SpriteSheet bombermanSheet = new SpriteSheet("/sprites/bomberman_sheet.png", 256, 128);

            spritesDict.put("idle", new Sprite[] {
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-down", new Sprite[] {
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-up", new Sprite[] {
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-left", new Sprite[] {
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-right", new Sprite[] {
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 48, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("dead", new Sprite[] {
                    new Sprite(22, 21, 4, 71, bombermanSheet, 16, 22),
                    new Sprite(22, 21, 26, 71, bombermanSheet, 16, 22),
                    new Sprite(22, 21, 48, 71, bombermanSheet, 16, 22),
                    new Sprite(22, 21, 70, 71, bombermanSheet, 16, 22),
                    new Sprite(22, 21, 92, 71, bombermanSheet, 16, 22),
                    new Sprite(22, 21, 114, 71, bombermanSheet, 16, 22),
            });
            initialized = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        switch (playerStatus) {
            case IDLE:
                gc.drawImage(spritesDict.get("idle")[direction.index]
                        .getFxImage(), this.x, this.y);
                break;
            case MOVING:
                gc.drawImage(spritesDict.get("moving-" + direction.label)[currentSpriteIndex / 6]
                        .getFxImage(), this.x, this.y);
                break;
            case DEAD:
                gc.drawImage(spritesDict.get("dead")[currentSpriteIndex / 6]
                        .getFxImage(), this.x, this.y);
                break;
        }
    }

    @Override
    public void update() {
        if (playerStatus == PlayerStatus.MOVING) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 6 >= spritesDict.get("moving-" + direction.label).length) {
                currentSpriteIndex = 0;
            }
            move(4, direction);
        }

        //Demo "die" status.
        //TODO: change it later.
        if (playerStatus == PlayerStatus.DEAD) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 6 >= spritesDict.get("dead").length) {
                currentSpriteIndex = 0;
                playerStatus = PlayerStatus.IDLE;
            }
        }
    }

    @Override
    public boolean isKilled() {
        return lives < 0;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move(int steps, Direction direction) {
        if (playerStatus == PlayerStatus.IDLE) {
            return;
        }
        switch (direction) {
            case DOWN:
                y += steps;
                break;
            case UP:
                y -= steps;
                break;
            case RIGHT:
                x += steps;
                break;
            case LEFT:
                x -= steps;
                break;
        }

        //TODO: add barrier checker.
    }
}
