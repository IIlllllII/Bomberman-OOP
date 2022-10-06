package uet.oop.bomberman.core;

import uet.oop.bomberman.components.entities.players.Bomber;
import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.GameConfig;

public class Camera {
    private double x;
    private double y;
    private int width;
    private int height;

    private Camera() {}

    private static class SingletonHelper {
        private static final Camera INSTANCE = new Camera();
    }

    public static Camera getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setInfo(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        Bomber bomber = EntitiesManager.getInstance().players.get(0);

        this.x = (bomber.getX() + bomber.getWidth() / 2.0) - GameConfig.WIDTH / 2.0;
        this.y = (bomber.getY() + bomber.getHeight() / 2.0) - GameConfig.HEIGHT / 2.0;

        if (this.x < 0) {
            this.x = 0;
        }

        if (this.y < 0) {
            this.y = 0;
        }

        LevelMap levelMap = LevelMap.getInstance();
        if (this.x > levelMap.getWidth() - this.width) {
            this.x = levelMap.getWidth() - this.width;
        }

        if (this.y > levelMap.getHeight() - this.height) {
            this.y = levelMap.getHeight() - this.height;
        }
    }
}
