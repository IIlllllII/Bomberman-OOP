package uet.oop.bomberman.components.entities.enemies.bosses;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.components.entities.BoxCollider;
import uet.oop.bomberman.components.entities.enemies.Enemy;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.graphics.SpriteSheet;
import uet.oop.bomberman.config.Direction;
import uet.oop.bomberman.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class Banana extends Enemy {
    private final Map<String, Animation> animationDict = new HashMap<>();

    private final BoxCollider movingBox;

    private final BoxCollider deathBox;

    private int lives = 1;

    public Banana(double x, double y) {
        super(x, y);
        this.setWidth((int) (47 * 1.5));
        this.setHeight((int) (74 * 1.5));

        movingBox = new BoxCollider(0, 0,15, 20);
        deathBox = new BoxCollider(0, 0, getWidth() - 20, 25);
        updateBoxCollider();

        animationDict.put("down",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 5,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("up",
                new Animation(SpriteSheet.normalBanana, 7, 7, 1000, 3, 164,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("left",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 84,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));
        animationDict.put("right",
                new Animation(SpriteSheet.normalBanana, 8, 8, 1000, 3, 247,
                47, 74, 47 * 1.5f, 74 * 1.5f, 4f, false));

        animationDict.put("dead",
                new Animation(SpriteSheet.deadBanana, 12, 12, 1200, 0, 0,
                76, 89, 76 * 1.5f, 89 * 1.5f, 3.3f, false));

        animationDict.get("down").setLoop(true);
        animationDict.get("up").setLoop(true);
        animationDict.get("left").setLoop(true);
        animationDict.get("right").setLoop(true);
        animationDict.get("dead").setLoop(false);

        initDirectionList();
        currentDirection = Direction.values()[r.nextInt(Direction.values().length)];
        score = 2000;
    }

    public BoxCollider getMovingBox() {
        return movingBox;
    }

    public BoxCollider getDeathBox() {
        return deathBox;
    }

    public void decreaseLives() {
        lives--;
        if (lives <= 0) {
            setDestroyed(true);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            animationDict.get(currentDirection.label)
                    .render(gc, x - camera.getX(), y - camera.getY());
        } else {
            animationDict.get("dead").render(gc, x - camera.getX(), y - camera.getY());
            if (! animationDict.get("dead").isDone()) {
                Text text = new Text();
                gc.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
                gc.setFill(Color.SNOW);
                gc.fillText(" + " + score,
                        x - camera.getX() + 16,
                        y - camera.getY() + 20 - animationDict.get("dead").getCalcTime() / 64);
            } else {
                done = true;
            }
        }
    }

    @Override
    public void update() {
        if (!destroyed) {
            move();
            if (animationDict.get(currentDirection.label) == null) {
                return;
            }
            animationDict.get(currentDirection.label).update();
            updateBoxCollider();
        } else {
            animationDict.get("dead").update();
        }
    }

    private void updateBoxCollider() {
        movingBox.setLocation(
                this.x + (this.width - movingBox.getWidth()) / 2.0,
                this.y + (this.height - movingBox.getHeight())
        );

        deathBox.setLocation(
                this.x + (this.width - deathBox.getWidth()) / 2.0,
                this.y + (this.height - deathBox.getHeight()) + 5
        );
    }

    @Override
    public boolean isDone() {
        return animationDict.get("dead").isDone();
    }

    @Override
    protected void move() {
        int j = (int) (movingBox.getX() / GameConfig.TILE_SIZE);
        int i = (int) (movingBox.getY() / GameConfig.TILE_SIZE);

        moveX = 0;
        moveY = 0;
        canMoveR = checkMapHash(i, j + 1);
        canMoveL = checkMapHash(i, j - 1);
        canMoveU = checkMapHash(i - 1, j);
        canMoveD = checkMapHash(i + 1, j);

        checkMove();
        if (moveY == 0 && moveX == 0) {
            if (directionList.size() != 0) {
                int ran = r.nextInt(directionList.size());
                currentDirection = directionList.get(ran);
            }
        }
        x += moveX;
        y += moveY;
    }
}
