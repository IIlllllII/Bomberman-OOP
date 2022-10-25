package uet.oop.bomberman.components.entities.bomber;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.*;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.items.item_types.Invincible;
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

public abstract class Bomber extends Entity {
    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;
    public static final double DEFAULT_SPEED = 2;

    private double initialX;        //reset in each level.
    private double initialY;        //reset in each level.
    private int lives = 3;
    protected double speed = DEFAULT_SPEED;
    protected boolean canPassBomb = false;
    protected boolean canPassFlame = false;
    protected boolean canPassBrick = false;
    private boolean invincible = false;
    private boolean canResetLocation = false;
    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    protected Action playerAction = Action.IDLE;
    protected Direction direction = Direction.DOWN;

    protected final BoxCollider bomberBox;

    public Bomber(double x, double y, int w, int h) {
        super(x, y, (int) ((1.2 * w * GameConfig.TILE_SIZE) / h), (int) (1.2 * GameConfig.TILE_SIZE));
        initialX = x;
        initialY = y;
        bomberBox = new BoxCollider(0, 0, 15, 20);
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
        resetLocation();
        move();
        if (playerAction == Action.IDLE) {
            return;
        }
        if (playerAction == Action.MOVING) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 4 >= spritesDict.get("moving-" + direction.label).length) {
                currentSpriteIndex = 0;
            }
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
                Invincible invincibleItem = new Invincible(x, y);
                invincibleItem.setTimePowerUp(3000);
                invincibleItem.setAppear(true);
                EntitiesManager.getInstance().items.add(invincibleItem);
                updateBoxCollider();
            }
        }
    }

    public abstract void updateBoxCollider();

    public void setInitialLocation(double x, double y) {
        this.initialX = x;
        this.initialY = y;
        updateBoxCollider();
    }

    public void placeBomb() {
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
                new Sound(Sound.PLACE_BOMB).play();
                bombList.add(new Bomb(bombX, bombY, 32, 32));

                LevelMap.getInstance().setHashAt(
                        bombY / GameConfig.TILE_SIZE,
                        bombX / GameConfig.TILE_SIZE,
                        "bomb");
            }
        }
    }

    public boolean isKilled() {
        return lives <= 0;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

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

    public Action getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(Action playerAction) {
        this.playerAction = playerAction;
    }

    public BoxCollider getBomberBox() {
        return bomberBox;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

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
    public void reset() {
        System.out.println("call reset bomber");
        lives = 3;
        canPassBrick = false;
        canPassBomb = false;
        canPassFlame = false;
    }
}
