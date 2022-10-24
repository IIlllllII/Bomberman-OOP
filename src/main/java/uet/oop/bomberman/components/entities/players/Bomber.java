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
import uet.oop.bomberman.config.CharacterStatus;
import uet.oop.bomberman.core.sound.Sound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bomber extends Entity implements Movable, Killable {
    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;
    public static final int DEFAULT_SPEED = 2;

    private final double initialX;
    private final double initialY;
    private int lives = 3;
    private int speed = DEFAULT_SPEED;
    private boolean canPassBomb = false;
    private boolean canPassFlame = false;
    private boolean canPassBrick = false;
    private boolean invincible = false;
    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    protected CharacterStatus playerStatus = CharacterStatus.IDLE;
    protected Direction direction = Direction.DOWN;

    protected final BoxCollider bomberBox;

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
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-down", new Sprite[]{
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-up", new Sprite[]{
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-left", new Sprite[]{
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22),
            });

            spritesDict.put("moving-right", new Sprite[]{
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 16, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 32, 26, bombermanSheet, 16, 22),
                    new Sprite(16, 22, 33 + 48, 26, bombermanSheet, 16, 22),
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
        Image image = null;
        switch (playerStatus) {
            case IDLE:
                image = spritesDict.get("idle")[direction.index].getFxImage();
                break;
            case MOVING:
                image = spritesDict.get("moving-" + direction.label)[currentSpriteIndex / 4].getFxImage();
                break;
            case DEAD:
                image = spritesDict.get("dead")[currentSpriteIndex / 6].getFxImage();
                gc.drawImage(image, this.x - camera.getX() - 3, this.y - camera.getY() + 2,
                        2 * 22 * 0.9, 2 * 21 * 0.9);
                break;
        }
        if (playerStatus == CharacterStatus.DEAD) {
            return;
        }
        gc.drawImage(image, this.x - camera.getX(), this.y - camera.getY(), this.width, this.height);
    }

    @Override
    public void update() {
        if (playerStatus == CharacterStatus.IDLE) {
            return;
        }
        if (playerStatus == CharacterStatus.MOVING) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 4 >= spritesDict.get("moving-" + direction.label).length) {
                currentSpriteIndex = 0;
            }
            move();
        }

        //TODO: change it later.
        if (playerStatus == CharacterStatus.DEAD) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 8 >= spritesDict.get("dead").length) {
                currentSpriteIndex = 0;
                lives--;
                playerStatus = CharacterStatus.IDLE;

                //Return to initial position:
                this.x = initialX;
                this.y = initialY;
                Initialized initialized = new Initialized(x, y);
                initialized.setTimePowerUp(5000);
                initialized.setAppear(true);
                EntitiesManager.getInstance().items.add(initialized);
                updateBoxCollider();
            }
        }
    }

    protected abstract void updateBoxCollider();

    public void setInitialLocation(int x, int y) {
        this.initialX = x;
        this.initialY = y;
    }

    public void placeBomb() {
        List<Bomb> bombList = EntitiesManager.getInstance().bombs;

        double centerX = bomberBox.getX() + bomberBox.getWidth() / 2;
        double centerY = bomberBox.getY() + bomberBox.getHeight() / 2;
        if (bombList.size() < bombMax) {
            int bombX = ((int) centerX / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            int bombY = ((int) centerY / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            boolean hasBomb = false;
            LevelMap levelMap = LevelMap.getInstance();
            if (levelMap.getHashAt(bombY / GameConfig.TILE_SIZE, bombX / GameConfig.TILE_SIZE)
                    != levelMap.getHash("grass")) {
                return;
            }
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setCanPassBrick(boolean canPassBrick) {
        this.canPassBrick = canPassBrick;
    }

    public void setCanResetLocation(boolean canResetLocation) {
        this.canResetLocation = canResetLocation;
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

    public CharacterStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(CharacterStatus playerStatus) {
        this.playerStatus = playerStatus;
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
    }

    public void resetLocation() {
        if (canResetLocation) {
            LevelMap levelMap = LevelMap.getInstance();
            double centerX = bomberBox.getX();
            double centerY = bomberBox.getY();
            int jBomber = ((int) centerX / GameConfig.TILE_SIZE);
            int iBomber = ((int) centerY / GameConfig.TILE_SIZE);
            boolean checkL = true;
            boolean checkR = true;
            boolean checkU = true;
            boolean checkD = true;
            if (levelMap.getHashAt(iBomber, jBomber) == levelMap.getHash("brick")) {
                int i = 1;
                while (true) {
                    if (jBomber - i >= 0) {
                        if (levelMap.getHashAt(iBomber, jBomber - i) == levelMap.getHash("wall")) {
                            checkL = false;
                        }
                        if (levelMap.getHashAt(iBomber, jBomber - i) == levelMap.getHash("grass") && checkL) {
                            x -= speed;
                            break;
                        }
                    }
                    if (jBomber + i < levelMap.getMapHash()[0].length) {
                        if (levelMap.getHashAt(iBomber, jBomber + i) == levelMap.getHash("wall")) {
                            checkR = false;
                        }
                        if (levelMap.getHashAt(iBomber, jBomber + i) == levelMap.getHash("grass") && checkR) {
                            x += speed;
                            break;
                        }
                    }
                    if (iBomber - i >= 0) {
                        if (levelMap.getHashAt(iBomber - i, jBomber) == levelMap.getHash("wall")) {
                            checkU = false;
                        }
                        if (levelMap.getHashAt(iBomber - i, jBomber) == levelMap.getHash("grass") && checkU) {
                            y -= speed;
                            break;
                        }
                    }
                    if (iBomber + i < levelMap.getMapHash().length) {
                        if (levelMap.getHashAt(iBomber + i, jBomber) == levelMap.getHash("wall")) {
                            checkD = false;
                        }
                        if (levelMap.getHashAt(iBomber + i, jBomber) == levelMap.getHash("grass") && checkD) {
                            y += speed;
                            break;
                        }
                    }
                    i++;
                }
                updateBoxCollider();
            } else {
                canResetLocation = false;
            }
        }
    }
    public void reset(){
        canPassBrick = false;
        canPassBomb = false;
        canPassFlame = false;
    }
}
