package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.config.GameConfig;

import javax.swing.*;

public class BoxCollider {
    private double x;
    private double y;
    private final double width;
    private final double height;

    public BoxCollider(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.TILE_SIZE;
        this.height = GameConfig.TILE_SIZE;
    }

    public BoxCollider(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isCollidedWith(BoxCollider other) {
        double left_1 = this.x;
        double right_1 = this.x + this.width;
        double top_1 = this.y;
        double bottom_1 = this.y + this.height;

        double left_2 = other.getX();
        double right_2 = other.getX() + other.getWidth();
        double top_2 = other.getY();
        double bottom_2 = other.getY() + other.getHeight();

//        return ((left_1 < right_2) &&
//                (right_1 > left_2) &&
//                (top_1 < bottom_2) &&
//                (bottom_1 > top_2));

        if (bottom_1 <= top_2) {
            return false;
        }
        if (top_1 >= bottom_2) {
            return false;
        }
        if (right_1 <= left_2) {
            return false;
        }
        if (left_1 >= right_2) {
            return false;
        }
        return true;
    }
}
