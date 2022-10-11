package uet.oop.bomberman.components.entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.components.entities.Entity;
import uet.oop.bomberman.components.graphics.Animation;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

public abstract class Enemy extends Entity {
    protected Animation animationLeft;
    protected Animation animationRight;
    protected Animation animationDeath;
    protected boolean destroyed = false;
    protected double moveX = 0;
    protected double moveY = 0;
    protected boolean canMoveR = false;
    protected boolean canMoveL = false;
    protected boolean canMoveU = false;
    protected boolean canMoveD = false;
    protected Direction lastDirection;
    protected boolean randomAnimation; // left or right
    protected int score;

    public Enemy(double x, double y) {
        super(x, y);
    }

    public abstract void render(GraphicsContext gc);

    public abstract void update();

    public boolean isDestroyed() {
        return destroyed;
    }

    protected abstract void move();

    protected boolean checkMapHash(int i, int j) {
        LevelMap levelMap = LevelMap.getInstance();
        if (i < 0 || i > (levelMap.getHeight() / 32) - 1 || j < 0 || j > (levelMap.getWidth() / 32) - 1) {
            return false;
        }
        return levelMap.getHashAt(i, j) == levelMap.getHash("grass");
    }
}
