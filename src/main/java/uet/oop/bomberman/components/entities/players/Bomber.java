package uet.oop.bomberman.components.entities.players;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.Killable;
import uet.oop.bomberman.components.entities.Movable;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.PlayerStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    public Bomber(int i, int j) {
        super(i, j);
    }

    private static final Map<String, List<Sprite>> spritesDict = new HashMap<>();
    private static boolean initialized = false;

    private int lives = 3;

    private int currentSpriteIndex = 0;

    private PlayerStatus playerStatus = PlayerStatus.IDLE;
    private Direction direction = Direction.DOWN;

    public static void init() {
        if (! initialized) {
            SpriteSheet bombermanSheet = new SpriteSheet("/sprites/bomberman_sheet.png", 256, 128);

            spritesDict.put("idle", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("moving-down", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("moving-up", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("moving-left", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("moving-right", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33 + 16, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33 + 48, 26, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("die", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                }
            });
            initialized = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (playerStatus == PlayerStatus.IDLE) {
            gc.drawImage(spritesDict
                    .get("idle").get(direction.index).getFxImage(), this.x, this.y);
        } else {
            gc.drawImage(spritesDict
                    .get("moving-" + direction.label).get(currentSpriteIndex / 6).getFxImage(), this.x, this.y);
        }
    }

    @Override
    public void update() {
        if (playerStatus == PlayerStatus.MOVING) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 6 >= spritesDict.get("moving-" + direction.label).size()) {
                currentSpriteIndex = 0;
            }
            move(4, direction);
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
    }
}
