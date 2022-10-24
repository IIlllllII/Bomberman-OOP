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
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.core.sound.Sound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;
    public static final int DEFAULT_SPEED = 2;

    private double initialX;        //reset in each level.
    private double initialY;        //reset in each level.
    private int lives = 3;
    private int speed = DEFAULT_SPEED;
    private boolean canPassBomb = false;
    private boolean canPassFlame = false;
    private boolean canPassBrick = false;
    private boolean invincible = false;
    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    private Action playerAction = Action.IDLE;
    private Direction direction = Direction.DOWN;

    private final BoxCollider bomberBox;

    public Bomber(double x, double y, int w, int h) {
        super(x, y, (int) ((1.2 * w * GameConfig.TILE_SIZE) / h), (int) (1.2 * GameConfig.TILE_SIZE));
        initialX = x;
        initialY = y;
        bomberBox = new BoxCollider(0, 0, 18, 20);
        updateBoxCollider();
    }

    public static void init() {
        if (!initialized) {
            SpriteSheet bombermanSheet = new SpriteSheet("/spriteSheet/bomberman_sheet.png", 256, 128);

            spritesDict.put("idle", new Sprite[]{
                    new Sprite(16, 22, 17, 2, bombermanSheet),
                    new Sprite(16, 22, 65, 2, bombermanSheet),
                    new Sprite(16, 22, 17, 26, bombermanSheet),
                    new Sprite(16, 22, 65, 26, bombermanSheet),
            });

            spritesDict.put("moving-down", new Sprite[]{
                    new Sprite(16, 22, 17, 2, bombermanSheet),
                    new Sprite(16, 22, 0, 2, bombermanSheet),
                    new Sprite(16, 22, 0, 2, bombermanSheet),
                    new Sprite(16, 22, 17, 2, bombermanSheet),
                    new Sprite(16, 22, 33, 2, bombermanSheet),
                    new Sprite(16, 22, 33, 2, bombermanSheet),
            });

            spritesDict.put("moving-up", new Sprite[]{
                    new Sprite(16, 22, 65, 2, bombermanSheet),
                    new Sprite(16, 22, 49, 2, bombermanSheet),
                    new Sprite(16, 22, 49, 2, bombermanSheet),
                    new Sprite(16, 22, 65, 2, bombermanSheet),
                    new Sprite(16, 22, 81, 2, bombermanSheet),
                    new Sprite(16, 22, 81, 2, bombermanSheet),
            });

            spritesDict.put("moving-left", new Sprite[]{
                    new Sprite(16, 22, 17, 26, bombermanSheet),
                    new Sprite(16, 22, 0, 26, bombermanSheet),
                    new Sprite(16, 22, 0, 26, bombermanSheet),
                    new Sprite(16, 22, 17, 26, bombermanSheet),
                    new Sprite(16, 22, 33, 26, bombermanSheet),
                    new Sprite(16, 22, 33, 26, bombermanSheet),
            });

            spritesDict.put("moving-right", new Sprite[]{
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet),
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet),
                    new Sprite(16, 22, 33 + 48, 26, bombermanSheet),
                    new Sprite(16, 22, 33 + 48, 26, bombermanSheet),
            });

            spritesDict.put("dead", new Sprite[]{
                    new Sprite(22, 21, 4, 71, bombermanSheet),
                    new Sprite(22, 21, 26, 71, bombermanSheet),
                    new Sprite(22, 21, 48, 71, bombermanSheet),
                    new Sprite(22, 21, 70, 71, bombermanSheet),
                    new Sprite(22, 21, 92, 71, bombermanSheet),
                    new Sprite(22, 21, 114, 71, bombermanSheet),
            });
            initialized = true;
        }
    }

    public void handleInput(List<KeyCode> inputList) {
        if (playerAction == Action.DEAD) {
            return;
        }

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

        if (inputList.contains(KeyCode.SPACE)) {
            placeBomb();
            inputList.remove(KeyCode.SPACE);
        }

        if (currentDirection != null) {
            playerAction = Action.MOVING;
            direction = currentDirection;
        } else {
            playerAction = Action.IDLE;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        Image image = null;
        switch (playerAction) {
            case IDLE:
                image = spritesDict.get("idle")[direction.index].getFxImage();
                break;
            case MOVING:
                image = spritesDict.get("moving-" + direction.label)[currentSpriteIndex / 4].getFxImage();
                break;
            case DEAD:
                image = spritesDict.get("dead")[currentSpriteIndex / 8].getFxImage();
                gc.drawImage(image, this.x - camera.getX() - 3, this.y - camera.getY() + 2,
                        2 * 22 * 0.9, 2 * 21 * 0.9);
                break;
        }
        if (playerAction == Action.DEAD) {
            return;
        }
        gc.drawImage(image, this.x - camera.getX(), this.y - camera.getY(), this.width, this.height);
    }

    @Override
    public void update() {
        if (playerAction == Action.IDLE) {
            return;
        }
        if (playerAction == Action.MOVING) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 4 >= spritesDict.get("moving-" + direction.label).length) {
                currentSpriteIndex = 0;
            }
            move();
        }

        if (playerAction == Action.DEAD) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 8 >= spritesDict.get("dead").length) {
                currentSpriteIndex = 0;
                lives--;
                playerAction = Action.IDLE;

                //Return to initial position:
                this.x = initialX;
                this.y = initialY;
                updateBoxCollider();
            }
        }
    }

    public void setInitialLocation(int x, int y) {
        this.initialX = x;
        this.initialY = y;
        updateBoxCollider();
    }

    private void updateBoxCollider() {
        bomberBox.setLocation(
                this.x + (this.width - bomberBox.getWidth()) / 2.0,
                this.y + bomberBox.getHeight() - 5
        );
    }
    public void placeBomb() {
        direction = Direction.DOWN;
        List<Bomb> bombList = EntitiesManager.getInstance().bombs;

        double centerX = bomberBox.getX() + bomberBox.getWidth() / 2;
        double centerY = bomberBox.getY() + bomberBox.getHeight() / 2;

        if (LevelMap.getInstance().getHashAt((int) centerY / GameConfig.TILE_SIZE, (int) centerX / GameConfig.TILE_SIZE)
            != LevelMap.getInstance().getHash("grass")) {
            return;
        }
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
            if (!hasBomb) {
                new Sound(Sound.PLACE_BOMB_SOUND).playSound();
                bombList.add(new Bomb(bombX, bombY, 32, 32));

                LevelMap.getInstance().setHashAt(
                        bombY / GameConfig.TILE_SIZE,
                        bombX / GameConfig.TILE_SIZE,
                        "bomb");
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

    public int getBombMax() {
        return bombMax;
    }

    public void setSpeed(int speed) {
        this.speed= speed;
    }

    public void setCanPassBrick(boolean canPassBrick) {
        this.canPassBrick = canPassBrick;
    }

    public boolean isCanPassBrick() {
        return canPassBrick;
    }

    public void setCanPassFlame(boolean canPassFlame) {
        this.canPassFlame = canPassFlame;
    }

    public boolean isCanPassFlame() {
        return canPassFlame;
    }

    public void setCanPassBomb(boolean canPassBomb) {
        this.canPassBomb = canPassBomb;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public Action getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(Action playerAction) {
        this.playerAction = playerAction;
    }

    public BoxCollider getBomberBox() {
        return bomberBox;
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
    public void move() {
        //Note: `steps` is always positive at first.
        int steps = speed;
        if (playerAction == Action.IDLE) {
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

        updateBoxCollider();

        int leftCol = (int) bomberBox.getX() / GameConfig.TILE_SIZE;
        int rightCol = (int) (bomberBox.getX() + bomberBox.getWidth()) / GameConfig.TILE_SIZE;
        int topRow = (int) bomberBox.getY() / GameConfig.TILE_SIZE;
        int bottomRow = (int) (bomberBox.getY() + bomberBox.getHeight()) / GameConfig.TILE_SIZE;

        //Barrier checker.
        boolean topLeftCheck = checkBarrier(topRow, leftCol);
        boolean topRightCheck = checkBarrier(topRow, rightCol);
        boolean bottomLeftCheck = checkBarrier(bottomRow, leftCol);
        boolean bottomRightCheck = checkBarrier(bottomRow, rightCol);

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

    private boolean checkBarrier(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();

        if (levelMap.getHashAt(i, j) == levelMap.getHash("bomb")) {
            if (EntitiesManager.getInstance().bombs.
                    get(EntitiesManager.getInstance().bombs.size() - 1).isAllowPass()) {
                return false;
            }
            return !canPassBomb;
        }
        if (levelMap.getHashAt(i, j) == levelMap.getHash("brick")) {
            return !canPassBrick;
        }

        if (levelMap.getHashAt(i, j) == levelMap.getHash("portal")) {
            if (EntitiesManager.getInstance().portal.isCanPass()) {
                levelMap.prepareNextLevel();
                return false;
            } else {
                return true;
            }
        }

        return levelMap.getHashAt(i, j) == levelMap.getHash("wall");
    }
}
