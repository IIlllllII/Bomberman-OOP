package uet.oop.bomberman.components.entities.players;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.entities.Killable;
import uet.oop.bomberman.components.entities.Movable;
import uet.oop.bomberman.components.graphics.Sprite;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;

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

    private Direction direction;
    public static void init() {
        if (! initialized) {
            SpriteSheet bombermanSheet = new SpriteSheet("/sprites/bomberman_sheet.png", 256, 128);

            spritesDict.put("down", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 0, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33, 2, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("up", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 49, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 65, 2, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 81, 2, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("right", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22));
                }
            });

            spritesDict.put("left", new ArrayList<>() {
                {
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 0, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 17, 26, bombermanSheet, 16, 22));
                    add(new Sprite(16, 22, 33, 26, bombermanSheet, 16, 22));
                }
            });
            initialized = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(spritesDict.get(direction.label).get(currentSpriteIndex / 6).getFxImage(), this.x, this.y);
    }

    @Override
    public void update() {
        //if (direction == Direction.DOWN) {
            currentSpriteIndex++;
            if (currentSpriteIndex / 6 == 4) {
                currentSpriteIndex = 0;
            }
            //System.out.println(currentSpriteIndex);
        //}
    }

    @Override
    public boolean isKilled() {
        return false;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move(int steps, Direction direction) {
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
