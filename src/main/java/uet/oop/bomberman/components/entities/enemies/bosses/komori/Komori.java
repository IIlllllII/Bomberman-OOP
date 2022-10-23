package uet.oop.bomberman.components.entities.enemies.bosses.komori;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Komori extends Enemy {
    private final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private final Image shadow;
    private double centerX;
    private double topY;
    private Action action;
    private int currentSpriteIndex = 0;
    private int flyingTime = 0;
    private int standingTime = 0;

    private int movingRandomTime = 0;

    private enum AttackTypes {
        LEFT("left"), RIGHT("right"), BOTH("both");
        public final String label;
        AttackTypes(String label) {
            this.label = label;
        }
    }
    private AttackTypes attackTypes;

    private boolean isFlying = true;

    public Komori(double centerX, double topY) {
        super(0, 0);       //These x, y coordinates are not be used.
        this.centerX = centerX;
        this.topY = topY;

        action = Action.APPEAR;

        SpriteSheet komoriSheet = new SpriteSheet("/spriteSheet/komori.png", 996, 102);
        spritesDict.put("appear", new Sprite[] {
                new Sprite(50, 102, 0, 0, komoriSheet),
                new Sprite(50, 102, 0, 0, komoriSheet),
                new Sprite(50, 102, 0, 0, komoriSheet),
                new Sprite(50, 102, 0, 0, komoriSheet),
                new Sprite(50, 102, 0, 0, komoriSheet),
                new Sprite(130 - 50, 102, 50, 0, komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
        });

        spritesDict.put("standing", new Sprite[] {
                new Sprite(50, 102, 0, 0, komoriSheet),
        });

        spritesDict.put("flying", new Sprite[] {
                new Sprite(252 - 130, 102, 130, 0, komoriSheet),
                new Sprite(336 - 252, 102, 252, 0, komoriSheet),
                new Sprite(384 - 336, 102, 336, 0, komoriSheet),
                new Sprite(384 - 336, 102, 336, 0, komoriSheet),
        });

        spritesDict.put("attack-" + AttackTypes.LEFT.label, new Sprite[] {
                new Sprite(441 - 384, 102, 384, 0, komoriSheet),
                new Sprite(541 - 441, 102, 441, 0, komoriSheet),
        });

        spritesDict.put("attack-" + AttackTypes.RIGHT.label, new Sprite[] {
                new Sprite( 598 - 541, 102, 541, 0, komoriSheet),
                new Sprite( 698 - 598, 102, 598, 0, komoriSheet),
        });

        spritesDict.put("attack-" + AttackTypes.BOTH.label, new Sprite[] {
                new Sprite(  757 - 698, 102, 698, 0, komoriSheet),
                new Sprite( 880 - 757, 102, 757, 0, komoriSheet),

        });

        spritesDict.put("dead", new Sprite[] {
                new Sprite(996 - 880, 102, 880, 0, komoriSheet),
        });

        shadow = SpriteSheet.shadow;
    }

    @Override
    public void render(GraphicsContext gc) {
        Sprite sprite = null;
        double imageX = 0;
        double imageY = 0;
        double imageWidth;
        double imageHeight;
        switch (action) {
            case APPEAR:
                sprite = spritesDict.get(action.label)[currentSpriteIndex / 8];
                if (currentSpriteIndex / 8 >= 0 || currentSpriteIndex / 8 <= 4) {
                    imageX += 7;
                }
                if (currentSpriteIndex / 8 == 5) {
                    imageX += 2;
                }
                if (currentSpriteIndex / 8 >= 6) {
                    imageX -= 20;
                }
                break;
            case IDLE:
                if (!isFlying) {
                    sprite = spritesDict.get("standing")[currentSpriteIndex / 8];
                    imageX += 7;
                    break;
                }
            case MOVING:
                sprite = spritesDict.get("flying")[currentSpriteIndex / 8];
                if (currentSpriteIndex / 8 == 0) {
                    imageX -= 12;
                }
                if (currentSpriteIndex / 8 == 1) {
                    imageX += 2;
                    imageY -= 1;
                }
                if (currentSpriteIndex / 8 == 2 || currentSpriteIndex / 8 == 3) {
                    imageX += 15;
                    imageY -= 2;
                }
                break;
            case ATTACK:
                if (attackTypes == null) {
                    attackTypes = AttackTypes.BOTH;
                }
                sprite = spritesDict.get(action.label + "-" + attackTypes.label)[currentSpriteIndex / 6];
                break;
            case DEAD:
                sprite = spritesDict.get(action.label)[currentSpriteIndex / 10];
                break;
        }
        imageX += centerX - sprite.getWidth() / 2.0;
        imageY += topY;
        imageWidth = sprite.getWidth();
        imageHeight = sprite.getHeight();

        if (isFlying) {
            imageY -= 30;
        }

        //Render shadow
        gc.drawImage(shadow, centerX + 10 - camera.getX(), topY + 118 - camera.getY(),
                32 * 1.5f, 12 * 1.5f);

        gc.drawImage(sprite.getFxImage(), imageX - camera.getX(), imageY - camera.getY(),
                imageWidth * 1.8f, imageHeight * 1.8f);
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void update() {
        currentSpriteIndex++;

        switch (action) {
            case APPEAR:    //Non-loop animation
                if (currentSpriteIndex / 8 >= spritesDict.get(action.label).length) {
                    currentSpriteIndex = 0;
                    action = Action.IDLE;
                    //action = Action.MOVING;
                }
                break;
            case IDLE:      //Loop animation
                if (!isFlying) {
                    if (currentSpriteIndex / 8 >= spritesDict.get("standing").length) {
                        currentSpriteIndex = 0;
                    }
                    break;
                }
            case MOVING:    //Loop animation
                if (currentSpriteIndex / 8 >= spritesDict.get("flying").length) {
                    currentSpriteIndex = 0;
                }
                break;
            case ATTACK:    //Non-loop animation
                attackTypes = AttackTypes.values()[(new Random()).nextInt(AttackTypes.values().length)];
                if (currentSpriteIndex / 6 >= spritesDict.get(action.label + "-" + attackTypes.label).length) {
                    currentSpriteIndex = 0;
                    action = Action.IDLE;
                }
                break;
            case DEAD:      //Non-loop animation
                if (currentSpriteIndex / 10 >= spritesDict.get(action.label).length) {
                    currentSpriteIndex = 0;
                    action = null;
                    setDestroyed(true);
                    done = true;
                }
                break;
        }

        if (action == Action.IDLE) {
            if (isFlying) {
                flyingTime++;
                if (flyingTime >= 500) {
                    isFlying = false;
                    flyingTime = 0;
                    currentSpriteIndex = 0;
                }
            } else {
                standingTime++;
                if (standingTime >= 200) {
                    isFlying = true;
                    standingTime = 0;
                    currentSpriteIndex = 0;
                }
            }
        }

        if (action == Action.MOVING) {
            //Default: flying high
            isFlying = true;
            move();
        }
    }

    @Override
    protected void move() {
//        moveX = 2;
//        lastDirection = Direction.LEFT;
//        //checkMove();
//        centerX += moveX;
    }
}
