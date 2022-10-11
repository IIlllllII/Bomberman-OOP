package uet.oop.bomberman.components.entities.players;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.components.entities.*;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.config.PlayerStatus;
import uet.oop.bomberman.sound.Sound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;
    private int lives = 3;
    private int steps = 4;
    private boolean canPassBomb = false;
    private boolean canPassFlame = false;
    private boolean canPassBrick = false;
    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    private PlayerStatus playerStatus = PlayerStatus.IDLE;
    private Direction direction = Direction.DOWN;

    private final BoxCollider bomberBox;

    public Bomber(double x, double y, int w, int h) {
        super(x, y, w * GameConfig.TILE_SIZE / h, GameConfig.TILE_SIZE);
        this.bomberBox = new BoxCollider(
                x + (this.width - 18) / 2.0, y + 18 - 2,
                18, 18
        );
    }

    public static void init() {
        if (!initialized) {
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

    public void handleInput(List<KeyCode> inputList) {
        Direction currentDirection = null;
        if (inputList.contains(KeyCode.RIGHT) || inputList.contains(KeyCode.D)) {
            currentDirection = Direction.RIGHT;
        }
        if (inputList.contains(KeyCode.LEFT) || inputList.contains(KeyCode.A)) {
            currentDirection = Direction.LEFT;
        }
        if (inputList.contains(KeyCode.UP) || inputList.contains(KeyCode.W)) {
            currentDirection = Direction.UP;
        }

        if (inputList.contains(KeyCode.DOWN) || inputList.contains(KeyCode.S)) {
            currentDirection = Direction.DOWN;
        }

        if  (inputList.contains(KeyCode.SPACE)) {
            placeBomb();
            inputList.remove(KeyCode.SPACE);
        }

        //Demo "die" status
        //TODO: remove it later.
        if (inputList.contains(KeyCode.M)) {
            playerStatus = PlayerStatus.DEAD;
        }

        if (currentDirection != null) {
            playerStatus = PlayerStatus.MOVING;
            direction = currentDirection;
        } else {
            if (playerStatus != PlayerStatus.DEAD) {
                playerStatus = PlayerStatus.IDLE;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        Image image = null;
        switch (playerStatus) {
            case IDLE:
                image = spritesDict.get("idle")[direction.index].getFxImage();
                break;
            case MOVING:
                image = spritesDict.get("moving-" + direction.label)[currentSpriteIndex / 6].getFxImage();
                break;
            case DEAD:
                image = spritesDict.get("dead")[currentSpriteIndex / 6].getFxImage();
                break;
        }
        gc.drawImage(image, this.x - camera.getX(), this.y - camera.getY(), this.width, this.height);
    }

    @Override
    public void update() {
        if (playerStatus == PlayerStatus.MOVING) {
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
    }

    public void placeBomb() {
        direction = Direction.DOWN;
        new Sound(Sound.PLACE_BOMB_SOUND).playSound();

        List<Bomb> bombList = EntitiesManager.getInstance().bombs;

        double centerX = bomberBox.getX() + bomberBox.getWidth() / 2;
        double centerY = bomberBox.getY() + bomberBox.getHeight() / 2;
        if (bombList.size() < bombMax) {
            int bombX = ((int) centerX / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            int bombY = ((int) centerY / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            boolean hasBomb = false;
            for (Bomb bomb : bombList) {
                if (bomb.getX() == bombX && bomb.getY() == bombY) {
                    hasBomb = true;
                    break;
                }
            }
            if (! hasBomb) {
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

    public void setCanPassBomb(boolean canPassBomb) {
        this.canPassBomb = canPassBomb;
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
            x -= steps;     //Move back
        }

        if ((y < 0) || (y + height > levelMap.getHeight())) {
            y -= steps;     //Move back
        }

        bomberBox.setLocation(x + (this.width - 18) / 2.0, y + 18 - 2);

        int leftCol = (int) bomberBox.getX() / GameConfig.TILE_SIZE;
        int rightCol = (int) (bomberBox.getX() + bomberBox.getWidth()) / GameConfig.TILE_SIZE;
        int topRow = (int) bomberBox.getY() / GameConfig.TILE_SIZE;
        int bottomRow = (int) (bomberBox.getY() + bomberBox.getHeight()) / GameConfig.TILE_SIZE;

        int wallHash = levelMap.getHash("wall");
        int brickHash = levelMap.getHash("brick");

        //Barrier checker.
        boolean topLeftCheck =
                ((levelMap.getHashAt(topRow, leftCol) == wallHash
                        || levelMap.getHashAt(topRow, leftCol) == brickHash));

        boolean topRightCheck =
                ((levelMap.getHashAt(topRow, rightCol) == wallHash
                        || levelMap.getHashAt(topRow, rightCol) == brickHash));

        boolean bottomLeftCheck =
                ((levelMap.getHashAt(bottomRow, leftCol) == wallHash
                        || levelMap.getHashAt(bottomRow, leftCol) == brickHash));

        boolean bottomRightCheck =
                ((levelMap.getHashAt(bottomRow, rightCol) == wallHash
                        || levelMap.getHashAt(bottomRow, rightCol) == brickHash));

        switch (direction) {
            case UP:
                if (topLeftCheck || topRightCheck) {
                    y -= steps;
                }
                break;
            case DOWN:
                if (bottomLeftCheck || bottomRightCheck) {
                    y -= steps;
                }
                break;
            case RIGHT:
                if (topRightCheck || bottomRightCheck) {
                    x -= steps;
                }
                break;
            case LEFT:
                if (topLeftCheck || bottomLeftCheck) {
                    x -= steps;
                }
                break;
        }
    }
}
