package uet.oop.bomberman.config;

public enum PlayerStatus {
    IDLE("idle"),
    MOVING("moving"),
    DIE("die");

    public final String label;
    PlayerStatus(String label) {
        this.label = label;
    }
}
