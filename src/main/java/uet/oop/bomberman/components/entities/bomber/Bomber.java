package uet.oop.bomberman.components.entities.bomber;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.BoxCollider;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.LivingEntity;
import uet.oop.bomberman.components.entities.bomb.Bomb;
import uet.oop.bomberman.components.entities.bomb.Flame;
import uet.oop.bomberman.components.entities.items.Item;
import uet.oop.bomberman.components.entities.items.item_types.Invincible;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;
import uet.oop.bomberman.core.scenes.game.BottomBar;
import uet.oop.bomberman.core.scenes.game.TopBar;
import uet.oop.bomberman.core.sound.Sound;

import java.util.*;

public abstract class Bomber extends LivingEntity {
    private static final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private static boolean initialized = false;
    public static final double DEFAULT_SPEED = 2;
    public static final int DEFAULT_LIVES = 3;

    private final List<Item> eatenItems = new ArrayList<>();

    private static SpriteSheet bombermanSheet;
    private static SpriteSheet bombermanFlashSheet;

    private double initialX;        //reset in each level.
    private double initialY;        //reset in each level.
    protected boolean canPassBomb = false;
    protected boolean canPassFlame = false;
    protected boolean canPassBrick = false;
    private boolean invincible = false;
    private int frameCounter;
    private boolean canResetLocation = false;
    private int bombMax = 1;
    private int currentSpriteIndex = 0;

    protected Action playerAction = Action.IDLE;

    protected final BoxCollider bomberBox;

    public Bomber(double x, double y, int w, int h) {
        super(x, y, (int) ((1.2 * w * GameConfig.TILE_SIZE) / h), (int) (1.2 * GameConfig.TILE_SIZE));
        initialX = x;
        initialY = y;
        speed = DEFAULT_SPEED;
        currentDirection = Direction.DOWN;
        lives = DEFAULT_LIVES;
        Bomb.setFlameLength(Bomb.DEFAULT_FLAME_LENGTH);
        TopBar.getInstance().setLives(lives);
        BottomBar.getInstance().setLives(lives);
        bomberBox = new BoxCollider(0, 0, 15, 20);
        updateBoxCollider();
    }

    public void updateBoxCollider() {
        bomberBox.setLocation(
                this.x + (this.width - bomberBox.getWidth()) / 2.0,
                this.y + bomberBox.getHeight() - 5
        );
    }

    public static void init() {
        if (!initialized) {
            bombermanSheet = new SpriteSheet("/spriteSheet/bomberman_sheet.png", 256, 128);
            bombermanFlashSheet = new SpriteSheet("/spriteSheet/bomberman_flash.png", 256, 128);

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
        Sprite sprite = null;
        switch (playerAction) {
            case IDLE:
                sprite = spritesDict.get("idle")[currentDirection.index];
                break;
            case MOVING:
                sprite = spritesDict.get("moving-" + currentDirection.label)[currentSpriteIndex / 4];
                break;
            case DEAD:
                sprite = spritesDict.get("dead")[currentSpriteIndex / 8];
                gc.drawImage(sprite.getFxImage(), this.x - camera.getX() - 3, this.y - camera.getY() + 2,
                        2 * 22 * 0.9, 2 * 21 * 0.9);
                break;
        }
        if (playerAction == Action.DEAD) {
            return;
        }
        if (invincible) {
            assert sprite != null;
            if ((frameCounter / 5) % 2 == 0) {
                sprite.setSheet(bombermanFlashSheet);
            } else {
                sprite.setSheet(bombermanSheet);
            }
            frameCounter++;
        } else {
            frameCounter = 0;
            sprite.setSheet(bombermanSheet);
        }
        gc.drawImage(sprite.getFxImage(), this.x - camera.getX(), this.y - camera.getY(), this.width, this.height);
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
            if (currentSpriteIndex / 4 >= spritesDict.get("moving-" + currentDirection.label).length) {
                currentSpriteIndex = 0;
            }
        }

        if (playerAction == Action.DEAD) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 8 >= spritesDict.get("dead").length) {
                currentSpriteIndex = 0;
                lives--;
                TopBar.getInstance().setLives(lives);
                BottomBar.getInstance().setLives(lives);
                playerAction = Action.IDLE;

                //Return to initial position:
                this.x = initialX;
                this.y = initialY;
                Invincible invincibleItem = new Invincible(x, y);
                invincibleItem.setTimePowerUp(3000);
                invincibleItem.setAppear(true);
                EntitiesManager.getInstance().items.add(invincibleItem);
                frameCounter = 1;
                updateBoxCollider();
            }
        }

        // Remove Pass item when it's done variable == true
        for(Iterator<Item> i = eatenItems.iterator(); i.hasNext();) {
            Item temp = i.next();
            if (temp.isDone() && temp.getTimePowerUp() > 0) {
                BottomBar.getInstance().remove(temp);
                i.remove();
            }
        }
    }

    public void setInitialLocation(double x, double y) {
        this.initialX = x;
        this.initialY = y;
        updateBoxCollider();
    }

    public void addItem(Item item) {
        eatenItems.add(item);
        BottomBar.getInstance().add(item);
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

    public void setBombMax(int bombMax) {
        this.bombMax = bombMax;
    }

    public int getBombMax() {
        return bombMax;
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
        eatenItems.clear();
        canPassBrick = false;
        canPassBomb = false;
        canPassFlame = false;
    }
}
