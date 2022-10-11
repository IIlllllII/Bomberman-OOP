package uet.oop.bomberman.components.entities.players;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.Killable;
import uet.oop.bomberman.components.entities.Movable;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.PlayerStatus;
import uet.oop.bomberman.sound.Music;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    public Bomber(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;

    private int lives = 3;
    private int steps = 4;
    private boolean canPassBom = false;
    private boolean canPassFlame = false;
    private boolean canPassBrick = false;

    private final List<Bomb> bombList = new ArrayList<>();

    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    private PlayerStatus playerStatus = PlayerStatus.IDLE;
    private Direction direction = Direction.DOWN;

    public static Music movingSound;

    public static void init() {
        if (!initialized) {
            movingSound = new Music(Music.MOVING_SOUND, true);
            SpriteSheet bombermanSheet = new SpriteSheet("/spriteSheet/bomberman_sheet.png", 256, 128);

            spritesDict.put("idle", new Sprite[]{
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-down", new Sprite[]{
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-up", new Sprite[]{
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-left", new Sprite[]{
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-right", new Sprite[]{
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 48, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("dead", new Sprite[]{
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
                        .getFxImage(), this.x - camera.getX(), this.y - camera.getY(), 16.0 * 32 / 22, 32);
                break;
            case MOVING:
                gc.drawImage(spritesDict.get("moving-" + direction.label)[currentSpriteIndex / 6]
                        .getFxImage(), this.x - camera.getX(), this.y - camera.getY(), 16.0 * 32 / 22, 32);
                break;
            case DEAD:
                gc.drawImage(spritesDict.get("dead")[currentSpriteIndex / 6]
                        .getFxImage(), this.x - camera.getX(), this.y - camera.getY(), 16.0 * 32 / 22, 32);
                break;
        }
        bombList.forEach(bomb -> bomb.render(gc));
    }

    @Override
    public void update() {
        if (playerStatus == PlayerStatus.MOVING) {
            movingSound.playMusic();
            currentSpriteIndex++;
            if (currentSpriteIndex / 6 >= spritesDict.get("moving-" + direction.label).length) {
                currentSpriteIndex = 0;
            }
            move(steps, direction);
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
        if (playerStatus == PlayerStatus.IDLE) {
            movingSound.stopMusic();
        }

        for (int i = 0; i < bombList.size(); i++) {
            if (!bombList.get(i).isDone()) {
                bombList.get(i).update();
            } else {
                bombList.remove(i);
                i--;
            }
        }
    }

    public void placeBomb() {
        if (bombList.size() < bombMax) {
            int bombX = (int) (getX() + getWidth() / 2) / 32 * 32;
            int bombY = (int) (getY() + getHeight() / 2) / 32 * 32;
            boolean hasBomb = false;
            for (Bomb bomb : bombList) {
                if (bomb.getX() == bombX && bomb.getY() == bombY) {
                    hasBomb = true;
                    break;
                }
            }
            if (!hasBomb) {
                new Sound(Sound.PLACE_BOMB_SOUND).playSound();
                bombList.add(new Bomb(bombX, bombY, 15, 15));
            }
        }
    }

    @Override
    public boolean isKilled() {
        return lives < 0;
    }

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public int getLives() {
        return lives;
    }

    public void setBombMax(int bombMax) {
        this.bombMax = bombMax;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setCanPassBrick(boolean canPassBrick) {
        this.canPassBrick = canPassBrick;
    }

    public void setCanPassFlame(boolean canPassFlame) {
        this.canPassFlame = canPassFlame;
    }

    public void setCanPassBom(boolean canPassBom) {
        this.canPassBom = canPassBom;
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
        //Note: `steps` is always positive when passed.

        if (playerStatus == PlayerStatus.IDLE) {
            return;
        }

        switch (direction) {
            case DOWN:
                y += steps;
                break;
            case UP:
                steps = -steps;
                y += steps;
                break;
            case RIGHT:
                x += steps;
                break;
            case LEFT:
                steps = -steps;
                x += steps;
                break;
        }

        LevelMap levelMap = LevelMap.getInstance();
        if ((x < 0) || (x + width > levelMap.getWidth())) {
            //Move back
            x -= steps;
        }

        if ((y < 0) || (y + height > levelMap.getHeight())) {
            //Move back
            y -= steps;
        }

        //TODO: add barrier checker.
    }
}
