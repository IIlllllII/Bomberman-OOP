package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.config.GameConfig;

public class BoxCollider {
    private double x;
    private double y;
    private double width;
    private double height;

    public BoxCollider(double x, double y) {
        this.x = x;
        this.y = y;
        width = GameConfig.TILE_SIZE;
        height = GameConfig.TILE_SIZE;
    }
}
