package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.BoxCollider;
import uet.oop.bomberman.components.entities.EntitiesManager;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Action;
import uet.oop.bomberman.config.GameConfig;

import java.util.*;

public class Komori extends Enemy {
    private final Map<String, Sprite[]> spritesDict = new HashMap<>();
    private double centerX;
    private double topY;
    private Action action;
    private int blood = 250;
    //private boolean isHurt = false;
    private final Image shadow;
    private final BoxCollider shadowBox;
    private final List<Weapon> weaponList = new ArrayList<>();
    private int currentSpriteIndex = 0;
    private int flyingTime = 0;
    private int standingTime = 0;
    private int movingTime = 0;
    private int deltaMovingTime = 0;
    private int deltaAttackTime = 0;

    private boolean addWeapon = false;

    private enum AttackSides {
        LEFT("left"), RIGHT("right"), BOTH("both");
        public final String label;
        AttackSides(String label) {
            this.label = label;
        }
    }
    private AttackSides attackSides;

    private boolean isFlying = true;

    public Komori(double centerX, double topY) {
        super(0, 0);       //These x, y coordinates are not be used.
        this.centerX = centerX;
        this.topY = topY;

        action = Action.APPEAR;
        shadow = SpriteSheet.shadow;
        shadowBox = new BoxCollider(0, 0, 26 * 1.5f, 12 * 1.5f);
        updateShadowBox();
        speed = 1;

        spritesDict.put("appear", new Sprite[] {
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
                new Sprite(130 - 50, 102, 50, 0, SpriteSheet.komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
        });

        spritesDict.put("standing", new Sprite[] {
                new Sprite(50, 102, 0, 0, SpriteSheet.komoriSheet),
        });

        spritesDict.put("flying", new Sprite[] {
                new Sprite(252 - 130, 102, 130, 0, SpriteSheet.komoriSheet),
                new Sprite(336 - 252, 102, 252, 0, SpriteSheet.komoriSheet),
                new Sprite(384 - 336, 102, 336, 0, SpriteSheet.komoriSheet),
                new Sprite(384 - 336, 102, 336, 0, SpriteSheet.komoriSheet),
        });

        spritesDict.put("attack-" + AttackSides.RIGHT.label, new Sprite[] {
                new Sprite(441 - 384, 102, 384, 0, SpriteSheet.komoriSheet),
                new Sprite(541 - 441, 102, 441, 0, SpriteSheet.komoriSheet),
                new Sprite(541 - 441, 102, 441, 0, SpriteSheet.komoriSheet),
                new Sprite(541 - 441, 102, 441, 0, SpriteSheet.komoriSheet),
        });

        spritesDict.put("attack-" + AttackSides.LEFT.label, new Sprite[] {
                new Sprite( 598 - 541, 102, 541, 0, SpriteSheet.komoriSheet),
                new Sprite( 698 - 598, 102, 598, 0, SpriteSheet.komoriSheet),
                new Sprite( 698 - 598, 102, 598, 0, SpriteSheet.komoriSheet),
                new Sprite( 698 - 598, 102, 598, 0, SpriteSheet.komoriSheet),
        });

        spritesDict.put("attack-" + AttackSides.BOTH.label, new Sprite[] {
                new Sprite(  757 - 698, 102, 698, 0, SpriteSheet.komoriSheet),
                new Sprite( 880 - 757, 102, 757, 0, SpriteSheet.komoriSheet),
                new Sprite( 880 - 757, 102, 757, 0, SpriteSheet.komoriSheet),
                new Sprite( 880 - 757, 102, 757, 0, SpriteSheet.komoriSheet),

        });

        spritesDict.put("dead", new Sprite[] {
                new Sprite(996 - 880, 102, 880, 0, SpriteSheet.komoriSheet),
        });
    }

    private void updateShadowBox() {
        shadowBox.setLocation(centerX + 10, topY + 118);
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
                if (attackSides == null) {
                    attackSides = AttackSides.BOTH;
                }
                sprite = spritesDict.get(action.label + "-" + attackSides.label)[currentSpriteIndex / 20];

                if (attackSides == AttackSides.RIGHT) {
                    if (currentSpriteIndex / 20 == 0) {
                        imageX -= 8;
                        imageY += 5;
                    } else {
                        imageX += 14;
                        imageY += 14;
                    }
                }

                if (attackSides == AttackSides.LEFT) {
                    if (currentSpriteIndex / 20 == 0) {
                        imageX += 32;
                        imageY += 5;
                    } else {
                        imageX -= 24;
                        imageY += 14;
                    }
                }
                if (attackSides == AttackSides.BOTH) {
                    if (currentSpriteIndex / 20 == 0) {
                        imageX += 10;
                        imageY += 10;
                    } else {
                        imageX -= 18;
                        imageY += 10;
                    }
                }
                break;
            case DEAD:
                sprite = spritesDict.get(action.label)[currentSpriteIndex / 30];
                imageX -= 10;
                break;
        }
        imageX += centerX - sprite.getWidth() / 2.0;
        imageY += topY;
        imageWidth = sprite.getWidth();
        imageHeight = sprite.getHeight();

        if (isFlying) {
            imageY -= 30;
        }

//        if (isHurt) {
//            sprite.setSheet(SpriteSheet.komoriFlashSheet);
//        } else {
//            sprite.setSheet(SpriteSheet.komoriSheet);
//        }
        //Render komori
        gc.drawImage(sprite.getFxImage(), imageX - camera.getX(), imageY - camera.getY(),
                imageWidth * 1.8f, imageHeight * 1.8f);

        //Render weapons
        weaponList.forEach(weapon -> {
            if (!weapon.isDone()) {
                weapon.render(gc);
            }
        });
    }

    public void renderShadow(GraphicsContext gc) {
        //Render shadow
        gc.drawImage(shadow, shadowBox.getX() - camera.getX(), shadowBox.getY() - camera.getY(),
                32 * 1.5f, 12 * 1.5f);
    }

    @Override
    public void update() {
        currentSpriteIndex++;
        updateShadowBox();

        if (action != Action.MOVING && action != Action.ATTACK) {
            deltaMovingTime++;
            if (deltaMovingTime >= 600) {
                setAction(Action.MOVING);
                deltaMovingTime = 0;
                addWeapon = false;
            }

            deltaAttackTime++;
            if (deltaAttackTime >= 900) {
                setAction(Action.ATTACK);
                attackSides = AttackSides.values()[(new Random()).nextInt(AttackSides.values().length)];
                deltaAttackTime = 0;
            }
        }

        switch (action) {
            case APPEAR:            //Non-loop animation
                if (currentSpriteIndex / 8 >= spritesDict.get(action.label).length) {
                    setAction(Action.ATTACK);
                    attackSides = AttackSides.BOTH;
                    //setAction(Action.IDLE);
                }
                break;
            case IDLE:              //Loop animation
                if (isFlying) {
                    if (currentSpriteIndex / 8 >= spritesDict.get("flying").length) {
                        currentSpriteIndex = 0;
                    }

                    flyingTime++;
                    if (flyingTime >= 500) {
                        isFlying = false;
                        flyingTime = 0;
                        currentSpriteIndex = 0;
                    }
                } else {
                    if (currentSpriteIndex / 8 >= spritesDict.get("standing").length) {
                        currentSpriteIndex = 0;
                    }

                    standingTime++;
                    if (standingTime >= 200) {
                        isFlying = true;
                        standingTime = 0;
                        currentSpriteIndex = 0;
                    }
                }
                break;
            case MOVING:            //Loop animation
                if (currentSpriteIndex / 8 >= spritesDict.get("flying").length) {
                    currentSpriteIndex = 0;
                }

                movingTime++;
                if (movingTime >= 100) {
                    movingTime = 0;
                    setAction(Action.IDLE);
                }

                isFlying = true;
                move();
                break;
            case ATTACK:            //Non-loop animation
                isFlying = false;
                if (currentSpriteIndex / 20 >= spritesDict.get(action.label + "-" + attackSides.label).length) {
                    setAction(Action.IDLE);
                }

                if (!addWeapon) {
                    weaponList.clear();
                    if (attackSides == AttackSides.LEFT || attackSides == AttackSides.BOTH) {
                        weaponList.add(new Weapon(centerX - 30, topY + 70, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.WHITE, Weapon.Type.NEAREST, 20));
                        weaponList.add(new Weapon(centerX - 60, topY + 70 - 5, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.WHITE, Weapon.Type.MIDDLE, 30));
                        weaponList.add(new Weapon(centerX - 80, topY + 70 - 10, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.WHITE, Weapon.Type.FARTHEST, 40));

                        weaponList.add(new Weapon(centerX - 20, topY + 70, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.CYAN, Weapon.Type.NEAREST, 45));
                        weaponList.add(new Weapon(centerX - 35, topY + 70 - 5, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.CYAN, Weapon.Type.MIDDLE, 55));
                        weaponList.add(new Weapon(centerX - 60, topY + 70 - 10, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.CYAN, Weapon.Type.FARTHEST, 65));

                        weaponList.add(new Weapon(centerX - 5, topY + 70, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.BLUE, Weapon.Type.NEAREST, 70));
                        weaponList.add(new Weapon(centerX - 30, topY + 70 - 5, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.BLUE, Weapon.Type.MIDDLE, 75));
                        weaponList.add(new Weapon(centerX - 50, topY + 70 - 10, 20, 20,
                                Weapon.Side.LEFT, Weapon.Color.BLUE, Weapon.Type.FARTHEST, 80));
                    }

                    if (attackSides == AttackSides.RIGHT || attackSides == AttackSides.BOTH) {
                        weaponList.add(new Weapon(centerX + 40, topY + 70, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.WHITE, Weapon.Type.NEAREST, 20));
                        weaponList.add(new Weapon(centerX + 70, topY + 70 - 5, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.WHITE, Weapon.Type.MIDDLE, 30));
                        weaponList.add(new Weapon(centerX + 90, topY + 70 - 10, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.WHITE, Weapon.Type.FARTHEST, 40));

                        weaponList.add(new Weapon(centerX + 40, topY + 70, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.CYAN, Weapon.Type.NEAREST, 45));
                        weaponList.add(new Weapon(centerX + 48, topY + 70 - 5, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.CYAN, Weapon.Type.MIDDLE, 55));
                        weaponList.add(new Weapon(centerX + 70, topY + 70 - 10, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.CYAN, Weapon.Type.FARTHEST, 65));

                        weaponList.add(new Weapon(centerX + 40, topY + 70, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.BLUE, Weapon.Type.NEAREST, 70));
                        weaponList.add(new Weapon(centerX + 45, topY + 70 - 5, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.BLUE, Weapon.Type.MIDDLE, 75));
                        weaponList.add(new Weapon(centerX + 60, topY + 70 - 10, 20, 20,
                                Weapon.Side.RIGHT, Weapon.Color.BLUE, Weapon.Type.FARTHEST, 80));
                    }
                    addWeapon = true;
                }
                break;
            case DEAD:              //Non-loop animation
                if (currentSpriteIndex / 30 >= spritesDict.get(action.label).length) {
                    currentSpriteIndex = 0;
                    setDestroyed(true);
                    done = true;
                }
                break;
        }

        //Update weapons
        boolean doneWeapon = true;
        for (Weapon weapon : weaponList) {
            if (!weapon.isDone()) {
                doneWeapon = false;
                weapon.update();
            }
        };
        if (doneWeapon) {
            weaponList.clear();
        }
    }

    @Override
    protected void move() {
        int j = (int) ((shadowBox.getX() + shadowBox.getWidth() / 2)) / GameConfig.TILE_SIZE;
        int i = (int) ((shadowBox.getY() + shadowBox.getHeight() / 2)) / GameConfig.TILE_SIZE;

        moveX = 0;
        moveY = 0;
        canMoveR = checkMapHash(i, j + 1);
        canMoveL = checkMapHash(i, j - 1);
        canMoveU = checkMapHash(i - 1, j);
        canMoveD = checkMapHash(i + 1, j);
        if ((i - 1) == 1) {
            canMoveU = false;
        }
        checkMove();
        if (moveY == 0 && moveX == 0 && directionList.size() != 0) {
            int ran = r.nextInt(directionList.size());
            lastDirection = directionList.get(ran);
        }
        centerX += moveX;
        topY += moveY;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void setAction(Action action) {
        this.action = action;
        currentSpriteIndex = 0;
    }

    public BoxCollider getShadowBox() {
        return shadowBox;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public boolean isHurt(boolean collide) {
        return (!isDestroyed()) && collide && (action != Action.DEAD) && !isFlying;
    }

    public void decreaseBlood() {
        blood -= 1;
        System.out.println("Blood: " + blood + " (" + String.format("%.2f", blood / 2.5) + "%)");
        if (blood <= 0) {
            //setDestroyed(true);
            action = Action.DEAD;
        }
    }

    public void checkWeaponCollision(BoxCollider bomberBox) {
        for (Weapon weapon : weaponList) {
            if (!weapon.isDone() && bomberBox.isCollidedWith(
                    new BoxCollider(weapon.getX(), weapon.getY(), 30, 30))) {
                EntitiesManager.getInstance().players.get(0).setPlayerAction(Action.DEAD);
            }
        }
    }
}

class Weapon extends Entity {
    private final Image image;
    private boolean done;
    private int delayFrames;
    enum Side {
        LEFT(0), RIGHT(1);
        public final int index;
        Side(int index) {
            this.index = index;
        }
    }
    enum Color {
        BLUE(0), CYAN(1), WHITE(2);
        public final int index;
        Color(int index) {
            this.index = index;
        }
    }
    enum Type {
        NEAREST(0), MIDDLE(1), FARTHEST(2);
        public final int index;
        Type(int index) {
            this.index = index;
        }
    }

    private final Side side;
    private final Color color;
    private final Type type;

    private static final SpriteSheet komoriWeaponSheet
            = new SpriteSheet("/spriteSheet/komori_weapon.png", 204, 46);

    private static final Sprite[][][] spriteDict = new Sprite[][][] {
            //LEFT
            {
                    //BLUE
                    {
                            //NEAREST
                            new Sprite(172 - 156, 23, 32, 0, komoriWeaponSheet),
                            //MIDDLE
                            new Sprite(188 - 172, 23, 16, 0, komoriWeaponSheet),
                            //FARTHEST
                            new Sprite(204 - 188, 23, 0, 0, komoriWeaponSheet),
                    },
                    //CYAN
                    {
                            new Sprite(109 - 84, 23, 95, 0, komoriWeaponSheet),
                            new Sprite(135 - 109, 23, 69, 0, komoriWeaponSheet),
                            new Sprite(156 - 135, 23, 48, 0, komoriWeaponSheet),
                    },
                    //WHITE
                    {
                            new Sprite(30, 23, 174, 0, komoriWeaponSheet),
                            new Sprite(30, 23, 144, 0, komoriWeaponSheet),
                            new Sprite(24, 23, 120, 0, komoriWeaponSheet),
                    },
            },
            //RIGHT
            {
                    //BLUE
                    {
                            new Sprite(172 - 156, 23, 156, 23, komoriWeaponSheet),
                            new Sprite(188 - 172, 23, 172, 23, komoriWeaponSheet),
                            new Sprite(204 - 188, 23, 188, 23, komoriWeaponSheet),
                    },
                    //CYAN
                    {
                            new Sprite(109 - 84, 23, 84, 23, komoriWeaponSheet),
                            new Sprite(135 - 109, 23, 109, 23, komoriWeaponSheet),
                            new Sprite(156 - 135, 23, 135, 23, komoriWeaponSheet),
                    },
                    //WHITE
                    {
                            new Sprite(30, 23, 0, 23, komoriWeaponSheet),
                            new Sprite(30, 23, 30, 23, komoriWeaponSheet),
                            new Sprite(24, 23, 60, 23, komoriWeaponSheet),
                    },
            },
    };

    Weapon(double x, double y, int width, int height, Side side, Color color, Type type, int delayFrames) {
        super(x, y, width, height);
        this.side = side;
        this.color = color;
        this.type = type;
        this.delayFrames = delayFrames;
        this.image = spriteDict[side.index][color.index][type.index].getFxImage();
    }

    @Override
    public void render(GraphicsContext gc) {
        if (delayFrames <= 0) {
            gc.drawImage(image, x - camera.getX(), y - camera.getY());
        }
    }

    @Override
    public void update() {
        if (delayFrames > 0) {
            delayFrames--;
            return;
        }

        //update x, y;
        int speed = 2;
        y += speed;
        if (side == Side.LEFT) {
            switch (type) {
                case NEAREST:
                    break;
                case MIDDLE:
                    x -= 0.2;
                    y += 0.1;
                    break;
                case FARTHEST:
                    x -= 0.6;
                    y += 0.1;
                    break;
            }
        } else {
            switch (type) {
                case NEAREST:
                    break;
                case MIDDLE:
                    x += 0.3;
                    break;
                case FARTHEST:
                    x += 0.6;
                    break;
            }
        }

        if (this.x < 0 || this.x > LevelMap.getInstance().getWidth()
            || this.y < 0 || this.y > LevelMap.getInstance().getHeight()) {
            done = true;
        }
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
