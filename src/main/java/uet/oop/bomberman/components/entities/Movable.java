package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.config.Direction;

public interface Movable {
    Direction getDirection();
    void setDirection(Direction direction);
    void move(int steps, Direction direction);
}
