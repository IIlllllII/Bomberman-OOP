package uet.oop.bomberman.config;

public enum Direction {
    IDLE("idle"),
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");

    public final String label;
    Direction(String label) {
        this.label = label;
    }
}
