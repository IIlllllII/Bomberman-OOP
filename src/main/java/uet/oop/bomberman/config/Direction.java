package uet.oop.bomberman.config;

public enum Direction {
    DOWN("down", 0),
    UP("up", 1),
    LEFT("left", 2),
    RIGHT("right", 3),
    STAND("stand", 4);


    public final String label;
    public final int index;
    Direction(String label, int index) {
        this.label = label;
        this.index = index;
    }
}
