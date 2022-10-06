package uet.oop.bomberman.components.entities;

import uet.oop.bomberman.components.maps.LevelMap;
import uet.oop.bomberman.config.Direction;

public interface Movable {
    public Direction getDirection();
    public void setDirection(Direction direction);
    public void move(int steps, Direction direction, LevelMap map);
}
